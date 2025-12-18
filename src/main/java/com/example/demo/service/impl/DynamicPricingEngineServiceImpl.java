package com.example.demo.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

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

    @Override
    public DynamicPriceRecord computeDynamicPrice(Long eventId) {
        EventRecord event = eventRepo.findById(eventId)
            .orElseThrow(() -> new IllegalArgumentException("Event not found"));
        if (!event.getActive()) throw new IllegalStateException("Event is inactive");

        SeatInventoryRecord inventory = inventoryRepo.findByEventId(eventId);
        if (inventory == null) throw new IllegalStateException("Inventory missing");

        List<PricingRule> rules = ruleRepo.findByActiveTrue();
        double price = event.getBasePrice();
        List<String> appliedCodes = new ArrayList<>();

        for (PricingRule rule : rules) {
            if (rule.appliesTo(inventory.getRemainingSeats(), event.getEventDate())) {
                price *= rule.getPriceMultiplier();
                appliedCodes.add(rule.getRuleCode());
            }
        }

        String appliedRuleCodes = String.join(",", appliedCodes);

        DynamicPriceRecord latest = priceRepo.findTopByEventIdOrderByComputedAtDesc(eventId);
        if (latest == null || !latest.getComputedPrice().equals(price)) {
            DynamicPriceRecord newRecord = new DynamicPriceRecord(eventId, price, appliedRuleCodes);
            priceRepo.save(newRecord);
            logRepo.save(new PriceAdjustmentLog(eventId,
                                                latest != null ? latest.getComputedPrice() : event.getBasePrice(),
                                                price,
                                                appliedRuleCodes,
                                                null));
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
