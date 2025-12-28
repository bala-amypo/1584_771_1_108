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

    // Required Constructor Injection [cite: 25, 185, 186]
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
        // Fetch event [cite: 190]
        EventRecord event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        // Validate event status [cite: 191]
        if (event.getActive() == null || !event.getActive()) {
            throw new BadRequestException("Event is not active");
        }

        // Fetch inventory [cite: 192]
        SeatInventoryRecord inventory = inventoryRepository.findByEventId(eventId)
                .orElseThrow(() -> new BadRequestException("Seat inventory not found"));

        // Fetch active rules [cite: 193]
        List<PricingRule> activeRules = ruleRepository.findByActiveTrue();
        
        long daysToEvent = ChronoUnit.DAYS.between(LocalDate.now(), event.getEventDate());
        double highestMultiplier = 1.0;
        String appliedCodes = "NONE";

        // Apply matching rules [cite: 194, 195]
        for (PricingRule rule : activeRules) {
            boolean seatMatch = inventory.getRemainingSeats() >= rule.getMinRemainingSeats() &&
                                inventory.getRemainingSeats() <= rule.getMaxRemainingSeats();
            boolean dateMatch = daysToEvent <= rule.getDaysBeforeEvent();

            if (seatMatch && dateMatch) {
                if (rule.getPriceMultiplier() > highestMultiplier) {
                    highestMultiplier = rule.getPriceMultiplier();
                    appliedCodes = rule.getRuleCode();
                }
            }
        }

        double computedPrice = event.getBasePrice() * highestMultiplier;

        // Log adjustment if price changed materially [cite: 197]
        Optional<DynamicPriceRecord> lastPriceOpt = priceRepository.findFirstByEventIdOrderByComputedAtDesc(eventId);
        double previousPrice = lastPriceOpt.isPresent() ? lastPriceOpt.get().getComputedPrice() : event.getBasePrice();

        if (Math.abs(computedPrice - previousPrice) > 0.01) {
            PriceAdjustmentLog log = new PriceAdjustmentLog();
            log.setEventId(eventId);
            log.setOldPrice(previousPrice);
            log.setNewPrice(computedPrice);
            log.setReason("Dynamic update: " + appliedCodes);
            logRepository.save(log);
        }

        // Save new price record [cite: 196]
        DynamicPriceRecord priceRecord = new DynamicPriceRecord();
        priceRecord.setEventId(eventId);
        priceRecord.setComputedPrice(computedPrice);
        priceRecord.setAppliedRuleCodes(appliedCodes);
        
        return priceRepository.save(priceRecord);
    }

    @Override
    public List<DynamicPriceRecord> getPriceHistory(Long eventId) {
        return priceRepository.findByEventIdOrderByComputedAtDesc(eventId); // [cite: 200]
    }

    @Override
    public Optional<DynamicPriceRecord> getLatestPrice(Long eventId) {
        return priceRepository.findFirstByEventIdOrderByComputedAtDesc(eventId); // [cite: 201]
    }

    @Override
    public List<DynamicPriceRecord> getAllComputedPrices() {
        return priceRepository.findAll(); // [cite: 202]
    }
}