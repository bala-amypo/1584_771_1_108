// src/main/java/com/example/demo/service/impl/DynamicPricingEngineServiceImpl.java
package com.example.demo.service.impl;

import com.example.demo.exception.BadRequestException;
import com.example.demo.model.*;
import com.example.demo.repository.*;
import com.example.demo.service.DynamicPricingEngineService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

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
        EventRecord event = eventRepository.findById(eventId).orElseThrow(() -> new RuntimeException("Event not found"));
        if (Boolean.FALSE.equals(event.getActive()))
            throw new BadRequestException("Event is not active");

        SeatInventoryRecord inv = inventoryRepository.findByEventId(eventId)
                .orElseThrow(() -> new RuntimeException("Seat inventory not found"));

        List<PricingRule> activeRules = ruleRepository.findByActiveTrue();
        int remaining = inv.getRemainingSeats() != null ? inv.getRemainingSeats() : 0;
        int daysToEvent = event.getEventDate() != null
                ? (int) ChronoUnit.DAYS.between(LocalDate.now(), event.getEventDate())
                : Integer.MAX_VALUE;

        List<PricingRule> matching = activeRules.stream()
                .filter(r -> r.getMinRemainingSeats() != null && r.getMaxRemainingSeats() != null
                        && remaining >= r.getMinRemainingSeats() && remaining <= r.getMaxRemainingSeats())
                .filter(r -> r.getDaysBeforeEvent() == null || daysToEvent <= r.getDaysBeforeEvent())
                .collect(Collectors.toList());

        double multiplier = 1.0;
        String appliedCodes = null;
        if (!matching.isEmpty()) {
            multiplier = matching.stream()
                    .map(PricingRule::getPriceMultiplier)
                    .filter(Objects::nonNull)
                    .max(Double::compareTo)
                    .orElse(1.0);
            appliedCodes = matching.stream().map(PricingRule::getRuleCode).collect(Collectors.joining(","));
        }

        double base = event.getBasePrice() != null ? event.getBasePrice() : 0.0;
        double computed = base * multiplier;

        Optional<DynamicPriceRecord> previousOpt = priceRepository.findFirstByEventIdOrderByComputedAtDesc(eventId);
        Double previousPrice = previousOpt.map(DynamicPriceRecord::getComputedPrice).orElse(null);

        DynamicPriceRecord rec = new DynamicPriceRecord();
        rec.setEventId(eventId);
        rec.setComputedPrice(computed);
        rec.setAppliedRuleCodes(appliedCodes);
        rec = priceRepository.save(rec);

        if (previousPrice == null || Math.abs(previousPrice - computed) > 0.0001) {
            PriceAdjustmentLog log = new PriceAdjustmentLog();
            log.setEventId(eventId);
            log.setOldPrice(previousPrice == null ? base : previousPrice);
            log.setNewPrice(computed);
            log.setReason("Dynamic price recomputed");
            logRepository.save(log);
        }

        return rec;
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