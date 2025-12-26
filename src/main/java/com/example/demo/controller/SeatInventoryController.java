package com.example.demo.controller;

import com.example.demo.model.SeatInventoryRecord;
import com.example.demo.service.SeatInventoryService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventory")
@SecurityRequirement(name = "bearerAuth")
public class SeatInventoryController {

    private final SeatInventoryService service;

    public SeatInventoryController(SeatInventoryService service) {
        this.service = service;
    }

    @PostMapping
    public SeatInventoryRecord create(@RequestBody SeatInventoryRecord record) {
        return service.createInventory(record);
    }

    @GetMapping("/{eventId}")
    public SeatInventoryRecord get(@PathVariable Long eventId) {
        return service.getInventoryByEvent(eventId);
    }
}
