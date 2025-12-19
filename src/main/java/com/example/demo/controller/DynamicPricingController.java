package com.example.demo.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.DynamicPriceRecord;
import com.example.demo.service.DynamicPricingEngineService;

@RestController
@RequestMapping("/pricing")
public class DynamicPricingController {

    private final DynamicPricingEngineService service;

    public DynamicPricingController(DynamicPricingEngineService service) {
        this.service = service;
    }

    @PostMapping("/compute/{eventId}")
    public ResponseEntity<DynamicPriceRecord> compute(@PathVariable Long eventId) {
        return ResponseEntity.ok(service.computeDynamicPrice(eventId));
    }

    @GetMapping("/history/{eventId}")
    public ResponseEntity<List<DynamicPriceRecord>> history(@PathVariable Long eventId) {
        return ResponseEntity.ok(service.getPriceHistory(eventId));
    }

    @GetMapping("/latest/{eventId}")
    public ResponseEntity<DynamicPriceRecord> latest(@PathVariable Long eventId) {
        return ResponseEntity.ok(service.getLatestPrice(eventId));
    }

    @GetMapping
    public ResponseEntity<List<DynamicPriceRecord>> getAll() {
        return ResponseEntity.ok(service.getAllComputedPrices());
    }
}
