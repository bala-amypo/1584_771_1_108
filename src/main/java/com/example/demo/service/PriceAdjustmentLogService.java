package com.example.demo.service;

import com.example.demo.model.PriceAdjustmentLog;

import java.util.List;

public interface PriceAdjustmentLogService {

    PriceAdjustmentLog create(PriceAdjustmentLog log);

    List<PriceAdjustmentLog> getByEvent(Long eventId);

    PriceAdjustmentLog getById(Long id);

    List<PriceAdjustmentLog> getAll();
}
