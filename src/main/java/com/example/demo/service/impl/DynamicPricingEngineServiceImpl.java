package com.example.demo.service.impl;

import com.example.demo.exception.BadRequestException;
import com.example.demo.model.*;
import com.example.demo.repository.*;
import com.example.demo.service.DynamicPricingEngineService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class DynamicPricingEngineServiceImpl implements DynamicPricingEngineService {

    private final EventRecordRepository eventRepository;
    private final SeatInventoryRecordRepository inventoryRepository;
    private final PricingRuleRepository ruleRepository;
    private final DynamicPriceRecordRepository priceRepository;
    private final PriceAdjustmentLogRepository logRepository;

    public DynamicPricingEngineServiceImpl(EventRecordRepository eventRepository,
                                           SeatInventoryRecordRepository inventoryRepository,
                                           PricingRuleRepository ruleRepository,
                                           DynamicPriceRecordRepository priceRepository,
                                           PriceAdjustmentLogRepository logRepository) {
        this.eventRepository = eventRepository;
        this.inventoryRepository = inventoryRepository;
        this.ruleRepository = ruleRepository;
        this.priceRepository = priceRepository;
        this.logRepository = logRepository;
    }

    @Override
    public DynamicPriceRecord computeDynamicPrice(Long eventId) {
        // 1. Fetch Event
        EventRecord event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        // 2. Validate Active
        if (!Boolean.TRUE.equals(event.getActive())) {
            throw new BadRequestException("Event is not active");
        }

        // 3. Fetch Inventory
        SeatInventoryRecord inventory = inventoryRepository.findByEventId(eventId)
                .orElseThrow(() -> new RuntimeException("Seat inventory not found"));

        // 4. Fetch Rules
        List<PricingRule> activeRules = ruleRepository.findByActiveTrue();

        // 5. Apply Rules
        double multiplier = 1.0;
        String appliedRules = "";
        
        long daysUntilEvent = ChronoUnit.DAYS.between(LocalDate.now(), event.getEventDate());
        
        double maxMultiplier = 1.0;
        String ruleCode = "";

        for (PricingRule rule : activeRules) {
            boolean seatMatch = inventory.getRemainingSeats() >= rule.getMinRemainingSeats() 
                             && inventory.getRemainingSeats() <= rule.getMaxRemainingSeats();
            
            boolean dateMatch = daysUntilEvent <= rule.getDaysBeforeEvent();

            if (seatMatch && dateMatch) {
                 if (rule.getPriceMultiplier() > maxMultiplier) {
                     maxMultiplier = rule.getPriceMultiplier();
                     ruleCode = rule.getRuleCode();
                 }
            }
        }
        
        multiplier = maxMultiplier;
        if (!ruleCode.isEmpty()) appliedRules = ruleCode;

        double computedPrice = event.getBasePrice() * multiplier;

        // 6. Check Previous Price and Log Adjustment
        Optional<DynamicPriceRecord> latest = priceRepository.findFirstByEventIdOrderByComputedAtDesc(eventId);
        if (latest.isPresent()) {
            double oldPrice = latest.get().getComputedPrice();
            if (Double.compare(oldPrice, computedPrice) != 0) {
                PriceAdjustmentLog log = new PriceAdjustmentLog();
                log.setEventId(eventId);
                log.setOldPrice(oldPrice);
                log.setNewPrice(computedPrice);
                log.setReason("Price changed by dynamic rule");
                logRepository.save(log);
            }
        }

        // 7. Save Record
        DynamicPriceRecord record = new DynamicPriceRecord();
        record.setEventId(eventId);
        record.setComputedPrice(computedPrice);
        record.setAppliedRuleCodes(appliedRules);
        
        return priceRepository.save(record);
    }

    @Override
    public List<DynamicPriceRecord> getPriceHistory(Long eventId) {
        return priceRepository.findByEventIdOrderByComputedAtDesc(eventId);
    }

    @Override
    public List<DynamicPriceRecord> getAllComputedPrices() {
        return priceRepository.findAll();
    }
}