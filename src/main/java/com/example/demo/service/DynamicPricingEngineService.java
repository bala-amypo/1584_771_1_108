package com.example.demo.service;

import java.util.List;

import com.example.demo.model.DynamicPriceRecord;

public interface DynamicPriceEngineService {
    DynamicPriceRecord computeDynamicPrice(Long eventId); 
    List<DynamicPriceRecord> getPriceHistory(Long eventId); 
    DynamicPriceRecord getLatestPrice(Long eventId); 
    List<DynamicPriceRecord> getAllComputedPrices();
}
