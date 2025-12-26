// src/main/java/com/example/demo/controller/DynamicPricingController.java
package com.example.demo.controller;

import com.example.demo.model.DynamicPriceRecord;
import com.example.demo.service.DynamicPricingEngineService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/dynamic-pricing")
@Tag(name = "Dynamic Pricing")
public class DynamicPricingController {
    private final DynamicPricingEngineService service;

    public DynamicPricingController(DynamicPricingEngineService service) {
        this.service = service;
    }

    @PostMapping("/compute/{eventId}")
    @Operation(summary = "Compute dynamic price")
    public DynamicPriceRecord compute(@PathVariable Long eventId) {
        return service.computeDynamicPrice(eventId);
    }

    @GetMapping("/latest/{eventId}")
    @Operation(summary = "Get latest price")
    public DynamicPriceRecord latest(@PathVariable Long eventId) {
        Optional<DynamicPriceRecord> opt = service.getLatestPrice(eventId);
        return opt.orElse(null);
    }

    @GetMapping("/history/{eventId}")
    @Operation(summary = "Get price history")
    public List<DynamicPriceRecord> history(@PathVariable Long eventId) {
        return service.getPriceHistory(eventId);
    }

    @GetMapping
    @Operation(summary = "List all computed prices")
    public List<DynamicPriceRecord> all() {
        return service.getAllComputedPrices();
    }
}