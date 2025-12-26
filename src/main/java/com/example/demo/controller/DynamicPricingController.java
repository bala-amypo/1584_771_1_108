package com.example.demo.controller;

import com.example.demo.model.PriceAdjustmentLog;
import com.example.demo.service.DynamicPricingEngineService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dynamic-pricing")
@SecurityRequirement(name = "bearerAuth")
public class DynamicPricingController {

    private final DynamicPricingEngineService service;

    public DynamicPricingController(DynamicPricingEngineService service) {
        this.service = service;
    }

    @PostMapping("/compute/{eventId}")
    public ResponseEntity<Double> compute(@PathVariable Long eventId) {
        return ResponseEntity.ok(service.computePrice(eventId));
    }

    @GetMapping("/latest/{eventId}")
    public ResponseEntity<PriceAdjustmentLog> getLatest(
            @PathVariable Long eventId) {
        return ResponseEntity.ok(service.getLatest(eventId));
    }

    @GetMapping("/history/{eventId}")
    public ResponseEntity<List<PriceAdjustmentLog>> getHistory(
            @PathVariable Long eventId) {
        return ResponseEntity.ok(service.getHistory(eventId));
    }

    @GetMapping
    public ResponseEntity<List<PriceAdjustmentLog>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }
}
