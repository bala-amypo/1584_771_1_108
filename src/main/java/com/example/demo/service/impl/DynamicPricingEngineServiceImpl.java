package com.example.demo.service.impl;

import com.example.demo.model.PriceAdjustmentLog;
import com.example.demo.repository.PriceAdjustmentLogRepository;
import com.example.demo.service.DynamicPricingEngineService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DynamicPricingEngineServiceImpl implements DynamicPricingEngineService {

    private final PriceAdjustmentLogRepository repository;

    public DynamicPricingEngineServiceImpl(PriceAdjustmentLogRepository repository) {
        this.repository = repository;
    }

    @Override
    public Double computePrice(Long eventId) {
        return 1000.0;
    }

    @Override
    public PriceAdjustmentLog getLatest(Long eventId) {
        return repository
                .findTopByEventIdOrderByChangedAtDesc(eventId)
                .orElse(null);
    }

    @Override
    public List<PriceAdjustmentLog> getHistory(Long eventId) {
        return repository.findByEventId(eventId);
    }

    @Override
    public List<PriceAdjustmentLog> getAll() {
        return repository.findAll();
    }
}
