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

    public PriceAdjustmentLog create(PriceAdjustmentLog log) {
        return repository.save(log);
    }

    public List<PriceAdjustmentLog> getAdjustmentsByEvent(long eventId) {
        return repository.findByEventId(eventId);
    }

    public List<PriceAdjustmentLog> getAll() {
        return repository.findAll();
    }
}
