// src/main/java/com/example/demo/controller/SeatInventoryController.java
package com.example.demo.controller;

import com.example.demo.model.SeatInventoryRecord;
import com.example.demo.service.SeatInventoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@Tag(name = "Inventory")
public class SeatInventoryController {
    private final SeatInventoryService service;

    public SeatInventoryController(SeatInventoryService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Create inventory")
    public SeatInventoryRecord create(@RequestBody SeatInventoryRecord inv) {
        return service.createInventory(inv);
    }

    @PutMapping("/{eventId}/remaining")
    @Operation(summary = "Update remaining seats")
    public SeatInventoryRecord update(@PathVariable Long eventId, @RequestParam Integer remaining) {
        return service.updateRemainingSeats(eventId, remaining);
    }

    @GetMapping("/event/{eventId}")
    @Operation(summary = "Get inventory by event id")
    public SeatInventoryRecord getByEvent(@PathVariable Long eventId) {
        return service.getInventoryByEvent(eventId).orElse(null);
    }

    @GetMapping
    @Operation(summary = "Get all inventories")
    public List<SeatInventoryRecord> getAll() {
        return service.getAllInventories();
    }
}