package com.example.demo.service;

import com.example.demo.model.DynamicPriceRecord;
import com.example.demo.model.PriceAdjustmentLog;

import java.util.List;

public interface DynamicPricingEngineService {

    double computeDynamicPrice(long eventId);

    List<PriceAdjustmentLog> getPriceHistory(long eventId);

    List<DynamicPriceRecord> getAllComputedPrices();
}
