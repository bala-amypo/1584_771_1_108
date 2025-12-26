// src/main/java/com/example/demo/controller/PriceAdjustmentLogController.java
package com.example.demo.controller;

import com.example.demo.model.PriceAdjustmentLog;
import com.example.demo.service.PriceAdjustmentLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/price-adjustments")
@Tag(name = "Price Adjustments")
public class PriceAdjustmentLogController {
    private final PriceAdjustmentLogService service;

    public PriceAdjustmentLogController(PriceAdjustmentLogService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Create adjustment log")
    public PriceAdjustmentLog create(@RequestBody PriceAdjustmentLog log) {
        return service.logAdjustment(log);
    }

    @GetMapping("/event/{eventId}")
    @Operation(summary = "Get adjustments by event")
    public List<PriceAdjustmentLog> byEvent(@PathVariable Long eventId) {
        return service.getAdjustmentsByEvent(eventId);
    }

    @GetMapping
    @Operation(summary = "Get all adjustments")
    public List<PriceAdjustmentLog> all() {
        return service.getAllAdjustments();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get adjustment by id")
    public PriceAdjustmentLog byId(@PathVariable Long id) {
        return service.getAllAdjustments().stream().filter(l -> l.getId().equals(id)).findFirst().orElse(null);
    }
}