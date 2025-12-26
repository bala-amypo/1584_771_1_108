package com.example.demo.controller;

import com.example.demo.model.DynamicPriceRecord;
import com.example.demo.service.DynamicPricingEngineService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pricing")
@SecurityRequirement(name = "bearerAuth")
public class DynamicPricingController {

    private final DynamicPricingEngineService service;

    public DynamicPricingController(DynamicPricingEngineService service) {
        this.service = service;
    }

    @PostMapping("/compute/{eventId}")
    public DynamicPriceRecord compute(@PathVariable Long eventId) {
        return service.computePrice(eventId);
    }

    @GetMapping("/history/{eventId}")
    public List<DynamicPriceRecord> history(@PathVariable Long eventId) {
        return service.getPriceHistory(eventId);
    }
}
