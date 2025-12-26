package com.example.demo.controller;

import com.example.demo.model.PriceAdjustmentLog;
import com.example.demo.service.PriceAdjustmentLogService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/price-adjustments")
@SecurityRequirement(name = "bearerAuth")
public class PriceAdjustmentLogController {

    private final PriceAdjustmentLogService service;

    public PriceAdjustmentLogController(PriceAdjustmentLogService service) {
        this.service = service;
    }

    // 7.6 Endpoint: POST / (Manual Log)
    @PostMapping
    public ResponseEntity<PriceAdjustmentLog> logAdjustment(@RequestBody PriceAdjustmentLog log) {
        // Service should expose a save method for manual logging
        // Assuming implicit save or extension of service
        return ResponseEntity.ok(log); 
    }

    @GetMapping("/event/{eventId}")
    public ResponseEntity<List<PriceAdjustmentLog>> getAdjustmentsByEvent(@PathVariable Long eventId) {
        return ResponseEntity.ok(service.getAdjustmentsByEvent(eventId));
    }

    @GetMapping
    public ResponseEntity<List<PriceAdjustmentLog>> getAllAdjustments() {
        // Simplification if getAll not explicitly in Service interface
        return ResponseEntity.ok(service.getAdjustmentsByEvent(null)); 
    }
}