package com.example.demo.service.impl;

import com.example.demo.model.DynamicPriceRecord;
import com.example.demo.model.EventRecord;
import com.example.demo.model.PricingRule;
import com.example.demo.repository.*;
import com.example.demo.service.DynamicPricingEngineService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DynamicPricingEngineServiceImpl implements DynamicPricingEngineService {

    private final EventRecordRepository eventRepo;
    private final SeatInventoryRecordRepository seatRepo;
    private final PricingRuleRepository ruleRepo;
    private final DynamicPriceRecordRepository dynamicRepo;
    private final PriceAdjustmentLogRepository logRepo;

    // ⚠ REQUIRED by test – DO NOT CHANGE
    public DynamicPricingEngineServiceImpl(
            EventRecordRepository eventRepo,
            SeatInventoryRecordRepository seatRepo,
            PricingRuleRepository ruleRepo,
            DynamicPriceRecordRepository dynamicRepo,
            PriceAdjustmentLogRepository logRepo
    ) {
        this.eventRepo = eventRepo;
        this.seatRepo = seatRepo;
        this.ruleRepo = ruleRepo;
        this.dynamicRepo = dynamicRepo;
        this.logRepo = logRepo;
    }

    @Override
    public DynamicPriceRecord computeDynamicPrice(long eventId) {
        EventRecord event = eventRepo.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        double price = event.getBasePrice();

        for (PricingRule rule : ruleRepo.findByActiveTrue()) {
            price = price * rule.getPriceMultiplier();
        }

        DynamicPriceRecord record = new DynamicPriceRecord();
        record.setEventId(eventId);
        record.setComputedPrice(price);
        record.setComputedAt(LocalDateTime.now());

        return dynamicRepo.save(record);
    }

    @Override
    public List<DynamicPriceRecord> getPriceHistory(long eventId) {
        return dynamicRepo.findByEventId(eventId);
    }

    @Override
    public List<DynamicPriceRecord> getAllComputedPrices() {
        return dynamicRepo.findAll();
    }
}
