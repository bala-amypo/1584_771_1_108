package com.example.demo.controller;

import com.example.demo.model.PriceAdjustmentLog;
import com.example.demo.service.PriceAdjustmentLogService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/price-adjustments")
public class PriceAdjustmentLogController {

    private final PriceAdjustmentLogService service;

    public PriceAdjustmentLogController(PriceAdjustmentLogService service) {
        this.service = service;
    }

    @GetMapping("/event/{eventId}")
    public List<PriceAdjustmentLog> getByEvent(@PathVariable long eventId) {
        return service.getAdjustmentsByEvent(eventId);
    }

    @GetMapping
    public List<PriceAdjustmentLog> getAll() {
        return service.getAll();
    }
}
