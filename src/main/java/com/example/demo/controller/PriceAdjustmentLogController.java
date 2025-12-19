package com.example.demo.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.PriceAdjustmentLog;
import com.example.demo.service.PriceAdjustmentLogService;

@RestController
@RequestMapping("/adjustments")
public class PriceAdjustmentLogController {

    private final PriceAdjustmentLogService service;

    public PriceAdjustmentLogController(PriceAdjustmentLogService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Void> log(@RequestBody PriceAdjustmentLog log) {
        service.logAdjustment(log);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<List<PriceAdjustmentLog>> getByEvent(@PathVariable Long eventId) {
        return ResponseEntity.ok(service.getAdjustmentsByEvent(eventId));
    }

    @GetMapping
    public ResponseEntity<List<PriceAdjustmentLog>> getAll() {
        return ResponseEntity.ok(service.getAllAdjustments());
    }
}
