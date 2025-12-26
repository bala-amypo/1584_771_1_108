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

    @PostMapping
    public ResponseEntity<PriceAdjustmentLog> create(
            @RequestBody PriceAdjustmentLog log) {
        return ResponseEntity.ok(service.create(log));
    }

    @GetMapping("/event/{eventId}")
    public ResponseEntity<List<PriceAdjustmentLog>> getByEvent(
            @PathVariable Long eventId) {
        return ResponseEntity.ok(service.getByEvent(eventId));
    }

    @GetMapping
    public ResponseEntity<List<PriceAdjustmentLog>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PriceAdjustmentLog> getById(
            @PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }
}
