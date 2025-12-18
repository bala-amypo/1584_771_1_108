package com.example.demo.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.model.PriceAdjustmentLog;
import com.example.demo.repository.PriceAdjustmentLogRepository;
import com.example.demo.service.PriceAdjustmentLogService;

@Service
public class PriceAdjustmentLogServiceImpl implements PriceAdjustmentLogService {

    private final PriceAdjustmentLogRepository repository;

    public PriceAdjustmentLogServiceImpl(PriceAdjustmentLogRepository repository) {
        this.repository = repository;
    }

    @Override
    public void logAdjustment(PriceAdjustmentLog log) {
        repository.save(log);
    }

    @Override
    public List<PriceAdjustmentLog> getAdjustmentsByEvent(Long eventId) {
        return repository.findByEventId(eventId);
    }

    @Override
    public List<PriceAdjustmentLog> getAllAdjustments() {
        return repository.findAll();
    }
}
