package com.example.demo.service.impl;

import com.example.demo.exception.BadRequestException;
import com.example.demo.model.*;
import com.example.demo.repository.*;
import com.example.demo.service.DynamicPricingEngineService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    // Strict constructor injection as required by project rules
    public DynamicPricingEngineServiceImpl(
            EventRecordRepository eventRepository,
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
    @Transactional
    public DynamicPriceRecord computeDynamicPrice(Long eventId) {
        // 1. Fetch event and validate existence
        EventRecord event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        // 2. Validate event is active
        if (event.getActive() == null || !event.getActive()) {
            throw new BadRequestException("Event is not active");
        }

        // 3. Fetch inventory and validate existence
        SeatInventoryRecord inventory = inventoryRepository.findByEventId(eventId)
                .orElseThrow(() -> new BadRequestException("Seat inventory not found"));

        // 4. Fetch active rules
        List<PricingRule> activeRules = ruleRepository.findByActiveTrue();
        
        long daysToEvent = ChronoUnit.DAYS.between(LocalDate.now(), event.getEventDate());
        double highestMultiplier = 1.0;
        StringBuilder appliedCodes = new StringBuilder();

        // 5. Apply matching rules
        for (PricingRule rule : activeRules) {
            boolean seatMatch = inventory.getRemainingSeats() >= rule.getMinRemainingSeats() &&
                                inventory.getRemainingSeats() <= rule.getMaxRemainingSeats();
            boolean dateMatch = daysToEvent <= rule.getDaysBeforeEvent();

            if (seatMatch && dateMatch) {
                if (rule.getPriceMultiplier() > highestMultiplier) {
                    highestMultiplier = rule.getPriceMultiplier();
                    appliedCodes = new StringBuilder(rule.getRuleCode());
                }
            }
        }

        double computedPrice = event.getBasePrice() * highestMultiplier;

        // 6. Log adjustment if price changed materially from the latest record
        Optional<DynamicPriceRecord> lastPriceOpt = priceRepository.findFirstByEventIdOrderByComputedAtDesc(eventId);
        double previousPrice = lastPriceOpt.isPresent() ? lastPriceOpt.get().getComputedPrice() : event.getBasePrice();

        if (Math.abs(computedPrice - previousPrice) > 0.01) {
            PriceAdjustmentLog log = new PriceAdjustmentLog();
            log.setEventId(eventId);
            log.setOldPrice(previousPrice);
            log.setNewPrice(computedPrice);
            log.setReason("Dynamic update: " + (appliedCodes.length() > 0 ? appliedCodes.toString() : "Base Price"));
            logRepository.save(log);
        }

        // 7. Save and return the new price record
        DynamicPriceRecord priceRecord = new DynamicPriceRecord();
        priceRecord.setEventId(eventId);
        priceRecord.setComputedPrice(computedPrice);
        priceRecord.setAppliedRuleCodes(appliedCodes.length() > 0 ? appliedCodes.toString() : "NONE");
        
        return priceRepository.save(priceRecord);
    }

    @Override
    public List<DynamicPriceRecord> getPriceHistory(Long eventId) {
        return priceRepository.findByEventIdOrderByComputedAtDesc(eventId);
    }

    @Override
    public Optional<DynamicPriceRecord> getLatestPrice(Long eventId) {
        return priceRepository.findFirstByEventIdOrderByComputedAtDesc(eventId);
    }

    @Override
    public List<DynamicPriceRecord> getAllComputedPrices() {
        return priceRepository.findAll();
    }
}