package com.example.demo.controller;

import com.example.demo.model.PriceAdjustmentLog;
import com.example.demo.service.DynamicPricingEngineService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dynamic-pricing")
public class DynamicPricingController {

    private final DynamicPricingEngineService service;

    public DynamicPricingController(DynamicPricingEngineService service) {
        this.service = service;
    }

    @PostMapping("/compute/{eventId}")
    public Double compute(@PathVariable Long eventId) {
        return service.computePrice(eventId);
    }

    @GetMapping("/latest/{eventId}")
    public PriceAdjustmentLog latest(@PathVariable Long eventId) {
        return service.getLatest(eventId);
    }

    @GetMapping("/history/{eventId}")
    public List<PriceAdjustmentLog> history(@PathVariable Long eventId) {
        return service.getHistory(eventId);
    }

    @GetMapping
    public List<PriceAdjustmentLog> getAll() {
        return service.getAll();
    }
}
