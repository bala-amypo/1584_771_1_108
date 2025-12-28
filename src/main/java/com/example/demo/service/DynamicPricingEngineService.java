package com.example.demo.service;

import com.example.demo.model.DynamicPriceRecord;
import java.util.List;
import java.util.Optional;

public interface DynamicPricingEngineService {
    DynamicPriceRecord computeDynamicPrice(Long eventId); // [cite: 188]
    List<DynamicPriceRecord> getPriceHistory(Long eventId); // [cite: 200]
    Optional<DynamicPriceRecord> getLatestPrice(Long eventId); // [cite: 201]
    List<DynamicPriceRecord> getAllComputedPrices(); // [cite: 202]
}