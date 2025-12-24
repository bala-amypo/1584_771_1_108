package com.example.demo.service.impl;

import com.example.demo.exception.BadRequestException;
import com.example.demo.model.*;
import com.example.demo.repository.*;
import com.example.demo.service.DynamicPricingEngineService;

import java.time.temporal.ChronoUnit;
import java.util.*;

public class DynamicPricingEngineServiceImpl implements DynamicPricingEngineService {

    private final EventRecordRepository eventRepo;
    private final SeatInventoryRecordRepository inventoryRepo;
    private final PricingRuleRepository ruleRepo;
    private final DynamicPriceRecordRepository priceRepo;
    private final PriceAdjustmentLogRepository logRepo;

    public DynamicPricingEngineServiceImpl(
            EventRecordRepository e,
            SeatInventoryRecordRepository i,
            PricingRuleRepository r,
            DynamicPriceRecordRepository p,
            PriceAdjustmentLogRepository l) {
        this.eventRepo = e;
        this.inventoryRepo = i;
        this.ruleRepo = r;
        this.priceRepo = p;
        this.logRepo = l;
    }

    public DynamicPriceRecord computeDynamicPrice(Long eventId) {
        EventRecord event = eventRepo.findById(eventId).orElseThrow();
        if (!event.getActive())
            throw new BadRequestException("Event is not active");

        SeatInventoryRecord inv = inventoryRepo.findByEventId(eventId)
                .orElseThrow(() -> new RuntimeException("Seat inventory not found"));

        long daysLeft = ChronoUnit.DAYS.between(
                java.time.LocalDate.now(), event.getEventDate());

        double multiplier = 1.0;
        List<String> applied = new ArrayList<>();

        for (PricingRule r : ruleRepo.findByActiveTrue()) {
            if (inv.getRemainingSeats() >= r.getMinRemainingSeats()
                    && inv.getRemainingSeats() <= r.getMaxRemainingSeats()
                    && daysLeft <= r.getDaysBeforeEvent()) {
                if (r.getPriceMultiplier() > multiplier) {
                    multiplier = r.getPriceMultiplier();
                    applied.add(r.getRuleCode());
                }
            }
        }

        double price = event.getBasePrice() * multiplier;

        DynamicPriceRecord record = new DynamicPriceRecord();
        record.setEventId(eventId);
        record.setComputedPrice(price);
        record.setAppliedRuleCodes(String.join(",", applied));
        priceRepo.save(record);

        Optional<DynamicPriceRecord> prev =
                priceRepo.findFirstByEventIdOrderByComputedAtDesc(eventId);

        if (prev.isPresent() && !prev.get().getComputedPrice().equals(price)) {
            PriceAdjustmentLog log = new PriceAdjustmentLog();
            log.setEventId(eventId);
            log.setOldPrice(prev.get().getComputedPrice());
            log.setNewPrice(price);
            logRepo.save(log);
        }

        return record;
    }

    public List<DynamicPriceRecord> getPriceHistory(Long eventId) {
        return priceRepo.findByEventIdOrderByComputedAtDesc(eventId);
    }

    public List<DynamicPriceRecord> getAllComputedPrices() {
        return priceRepo.findAll();
    }
}
