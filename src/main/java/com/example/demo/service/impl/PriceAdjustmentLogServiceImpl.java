package com.example.demo.service.impl;

import com.example.demo.model.PriceAdjustmentLog;
import com.example.demo.repository.PriceAdjustmentLogRepository;
import com.example.demo.service.PriceAdjustmentLogService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PriceAdjustmentLogServiceImpl implements PriceAdjustmentLogService {

    private final PriceAdjustmentLogRepository repository;

    public PriceAdjustmentLogServiceImpl(PriceAdjustmentLogRepository repository) {
        this.repository = repository;
    }

    @Override
    public PriceAdjustmentLog create(PriceAdjustmentLog log) {
        return repository.save(log);
    }

    @Override
    public List<PriceAdjustmentLog> getByEvent(Long eventId) {
        return repository.findByEventId(eventId);
    }

    @Override
    public PriceAdjustmentLog getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<PriceAdjustmentLog> getAll() {
        return repository.findAll();
    }
}
