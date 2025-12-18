package com.example.demo.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.DynamicPriceRecord;
import com.example.demo.model.EventRecord;
import com.example.demo.model.PriceAdjustmentLog;
import com.example.demo.model.PricingRule;
import com.example.demo.model.SeatInventoryRecord;
import com.example.demo.repository.DynamicPriceRecordRepository;
import com.example.demo.repository.EventRecordRepository;
import com.example.demo.repository.PriceAdjustmentLogRepository;
import com.example.demo.repository.PricingRuleRepository;
import com.example.demo.repository.SeatInventoryRecordRepository;
import com.example.demo.service.DynamicPricingEngineService;

@Service
public class DynamicPricingEngineServiceImpl implements DynamicPricingEngineService {

    private final EventRecordRepository eventRepo;
    private final SeatInventoryRecordRepository inventoryRepo;
    private final PricingRuleRepository ruleRepo;
    private final DynamicPriceRecordRepository priceRepo;
    private final PriceAdjustmentLogRepository logRepo;

    public DynamicPricingEngineServiceImpl(
        EventRecordRepository eventRepo,
        SeatInventoryRecordRepository inventoryRepo,
        PricingRuleRepository ruleRepo,
        DynamicPriceRecordRepository priceRepo,
        PriceAdjustmentLogRepository logRepo
    ) {
        this.eventRepo = eventRepo;
        this.inventoryRepo = inventoryRepo;
        this.ruleRepo = ruleRepo;
        this.priceRepo = priceRepo;
        this.logRepo = logRepo;
    }

    @Transactional
    @Override
    public DynamicPriceRecord computeDynamicPrice(Long eventId) {
        EventRecord event = eventRepo.findById(eventId)
            .orElseThrow(() -> new IllegalArgumentException("Event not found"));
        if (Boolean.FALSE.equals(event.getActive())) {
            throw new IllegalStateException("Event is inactive");
        }

        SeatInventoryRecord inventory = inventoryRepo.findByEventId(eventId);
        if (inventory == null) {
            throw new IllegalStateException("Inventory missing");
        }
        if (inventory.getRemainingSeats() != null && inventory.getTotalSeats() != null
                && inventory.getRemainingSeats() > inventory.getTotalSeats()) {
            throw new IllegalStateException("Remaining seats exceed total seats");
        }

        List<PricingRule> rules = ruleRepo.findByActiveTrue();

        BigDecimal price = BigDecimal.valueOf(event.getBasePrice());
        List<String> appliedCodes = new ArrayList<>();

        for (PricingRule rule : rules) {
            Integer remaining = inventory.getRemainingSeats();
            LocalDate eventDate = event.getEventDate();
            long daysToEvent = eventDate != null ? ChronoUnit.DAYS.between(LocalDate.now(), eventDate) : -1;

            boolean seatsOk = remaining != null
                    && rule.getMinRemainingSeats() != null
                    && rule.getMaxRemainingSeats() != null
                    && remaining >= rule.getMinRemainingSeats()
                    && remaining <= rule.getMaxRemainingSeats();

            boolean daysOk = rule.getDaysBeforeEvent() != null
                    && daysToEvent >= rule.getDaysBeforeEvent();

            if (Boolean.TRUE.equals(rule.getActive()) && seatsOk && daysOk) {
                price = price.multiply(BigDecimal.valueOf(rule.getPriceMultiplier()));
                appliedCodes.add(rule.getRuleCode());
            }
        }

        if (price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalStateException("Computed price must be greater than 0");
        }

        String appliedRuleCodes = appliedCodes.isEmpty() ? null : String.join(",", appliedCodes);

        DynamicPriceRecord latest = priceRepo.findTopByEventIdOrderByComputedAtDesc(eventId);
        BigDecimal latestPrice = latest != null ? BigDecimal.valueOf(latest.getComputedPrice()) : null;

        boolean materiallyChanged = latestPrice == null
                || price.subtract(latestPrice).abs().compareTo(BigDecimal.valueOf(0.01)) > 0;

        if (materiallyChanged) {
            DynamicPriceRecord newRecord = new DynamicPriceRecord(eventId, price.doubleValue(), appliedRuleCodes);
            priceRepo.save(newRecord);

            Double oldPrice = latestPrice != null ? latestPrice.doubleValue() : event.getBasePrice();
            String reason = appliedRuleCodes != null
                    ? "Material price change due to rules: " + appliedRuleCodes
                    : "Material price change (no rules applied)";

            PriceAdjustmentLog log = new PriceAdjustmentLog(
                eventId,
                oldPrice,
                price.doubleValue(),
                reason,
                null // changedAt auto-set via @PrePersist
            );
            logRepo.save(log);

            return newRecord;
        }

        return latest;
    }

    @Override
    public List<DynamicPriceRecord> getPriceHistory(Long eventId) {
        return priceRepo.findByEventIdOrderByComputedAtDesc(eventId);
    }

    @Override
    public DynamicPriceRecord getLatestPrice(Long eventId) {
        return priceRepo.findTopByEventIdOrderByComputedAtDesc(eventId);
    }

    @Override
    public List<DynamicPriceRecord> getAllComputedPrices() {
        return priceRepo.findAll();
    }
}
