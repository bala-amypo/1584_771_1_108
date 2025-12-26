package com.example.demo.service;

import com.example.demo.model.DynamicPriceRecord;

import java.util.List;

public interface DynamicPricingEngineService {

    DynamicPriceRecord computeDynamicPrice(long eventId);

    List<DynamicPriceRecord> getPriceHistory(long eventId);

    List<DynamicPriceRecord> getAllComputedPrices();
}
