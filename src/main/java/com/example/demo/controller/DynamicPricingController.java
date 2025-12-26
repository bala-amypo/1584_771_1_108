package com.example.demo.controller;

import com.example.demo.model.DynamicPriceRecord;
import com.example.demo.model.PriceAdjustmentLog;
import com.example.demo.service.DynamicPricingEngineService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dynamic-pricing")
public class DynamicPricingController {

    private final DynamicPricingEngineService service;

    public DynamicPricingController(DynamicPricingEngineService service) {
        this.service = service;
    }

    @GetMapping("/compute/{eventId}")
    public double compute(@PathVariable long eventId) {
        return service.computeDynamicPrice(eventId);
    }

    @GetMapping("/history/{eventId}")
    public List<PriceAdjustmentLog> history(@PathVariable long eventId) {
        return service.getPriceHistory(eventId);
    }

    @GetMapping
    public List<DynamicPriceRecord> all() {
        return service.getAllComputedPrices();
    }
}
