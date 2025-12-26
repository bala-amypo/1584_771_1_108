package com.example.demo.service;

import com.example.demo.model.PriceAdjustmentLog;
import java.util.List;

public interface DynamicPricingEngineService {
    Double computePrice(Long eventId);
    PriceAdjustmentLog getLatest(Long eventId);
    List<PriceAdjustmentLog> getHistory(Long eventId);
    List<PriceAdjustmentLog> getAll();
}
