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
        EventRecord event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found")); // [cite: 190]

        if (event.getActive() == null || !event.getActive()) {
            throw new BadRequestException("Event is not active"); // [cite: 191]
        }

        SeatInventoryRecord inventory = inventoryRepository.findByEventId(eventId)
                .orElseThrow(() -> new BadRequestException("Seat inventory not found")); // [cite: 192]

        List<PricingRule> activeRules = ruleRepository.findByActiveTrue(); // [cite: 193]
        
        long daysToEvent = ChronoUnit.DAYS.between(LocalDate.now(), event.getEventDate());
        double highestMultiplier = 1.0;
        String appliedCodes = "NONE";

        // Find highest matching multiplier [cite: 194, 195]
        for (PricingRule rule : activeRules) {
            boolean seatsInRange = inventory.getRemainingSeats() >= rule.getMinRemainingSeats() &&
                                   inventory.getRemainingSeats() <= rule.getMaxRemainingSeats();
            
            if (seatsInRange && daysToEvent <= rule.getDaysBeforeEvent()) {
                if (rule.getPriceMultiplier() > highestMultiplier) {
                    highestMultiplier = rule.getPriceMultiplier();
                    appliedCodes = rule.getRuleCode();
                }
            }
        }

        double computedPrice = event.getBasePrice() * highestMultiplier; // [cite: 195]

        // Handle Material Price Change Logging [cite: 197]
        Optional<DynamicPriceRecord> lastPrice = priceRepository.findFirstByEventIdOrderByComputedAtDesc(eventId);
        double previousPrice = lastPrice.isPresent() ? lastPrice.get().getComputedPrice() : event.getBasePrice();

        if (Math.abs(computedPrice - previousPrice) > 0.01) {
            PriceAdjustmentLog log = new PriceAdjustmentLog();
            log.setEventId(eventId);
            log.setOldPrice(previousPrice);
            log.setNewPrice(computedPrice);
            log.setReason("Rule Applied: " + appliedCodes);
            logRepository.save(log);
        }

        DynamicPriceRecord record = new DynamicPriceRecord();
        record.setEventId(eventId);
        record.setComputedPrice(computedPrice);
        record.setAppliedRuleCodes(appliedCodes);
        return priceRepository.save(record); // [cite: 196]
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