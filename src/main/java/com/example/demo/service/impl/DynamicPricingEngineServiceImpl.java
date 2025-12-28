package com.example.demo.service.impl;

import com.example.demo.exception.BadRequestException;
import com.example.demo.model.*;
import com.example.demo.repository.*;
import com.example.demo.service.DynamicPricingEngineService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    @Transactional
    public DynamicPriceRecord computeDynamicPrice(Long eventId) {
        EventRecord event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        if (!event.getActive()) throw new BadRequestException("Event is not active"); [cite: 191]

        SeatInventoryRecord inv = inventoryRepository.findByEventId(eventId)
                .orElseThrow(() -> new BadRequestException("Seat inventory not found")); [cite: 192]

        List<PricingRule> activeRules = ruleRepository.findByActiveTrue(); [cite: 193]
        
        // Logic to find highest multiplier [cite: 195]
        double highestMultiplier = 1.0;
        String appliedCodes = "BASE";
        
        for (PricingRule rule : activeRules) {
            if (inv.getRemainingSeats() >= rule.getMinRemainingSeats() && 
                inv.getRemainingSeats() <= rule.getMaxRemainingSeats()) {
                if (rule.getPriceMultiplier() > highestMultiplier) {
                    highestMultiplier = rule.getPriceMultiplier();
                    appliedCodes = rule.getRuleCode();
                }
            }
        }

        double newPrice = event.getBasePrice() * highestMultiplier; [cite: 195]

        // Check for material change before logging 
        Optional<DynamicPriceRecord> lastRecord = priceRepository.findFirstByEventIdOrderByComputedAtDesc(eventId);
        double oldPrice = lastRecord.map(DynamicPriceRecord::getComputedPrice).orElse(event.getBasePrice());

        if (Math.abs(newPrice - oldPrice) > 0.01) {
            PriceAdjustmentLog log = new PriceAdjustmentLog();
            log.setEventId(eventId);
            log.setOldPrice(oldPrice);
            log.setNewPrice(newPrice);
            log.setReason("Price updated based on rule: " + appliedCodes);
            logRepository.save(log); [cite: 197]
        }

        DynamicPriceRecord newRecord = new DynamicPriceRecord();
        newRecord.setEventId(eventId);
        newRecord.setComputedPrice(newPrice);
        newRecord.setAppliedRuleCodes(appliedCodes);
        return priceRepository.save(newRecord); [cite: 196]
    }
}