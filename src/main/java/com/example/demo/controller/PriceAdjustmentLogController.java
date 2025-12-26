package com.example.demo.controller;

import com.example.demo.model.PriceAdjustmentLog;
import com.example.demo.service.PriceAdjustmentLogService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/adjustments")
@SecurityRequirement(name = "bearerAuth")
public class PriceAdjustmentController {

    private final PriceAdjustmentLogService service;

    public PriceAdjustmentController(PriceAdjustmentLogService service) {
        this.service = service;
    }

    @GetMapping("/event/{eventId}")
    public List<PriceAdjustmentLog> getByEvent(@PathVariable Long eventId) {
        return service.getAdjustmentsByEvent(eventId);
    }
}
