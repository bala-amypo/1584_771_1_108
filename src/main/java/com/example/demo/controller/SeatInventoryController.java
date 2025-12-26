package com.example.demo.controller;

import com.example.demo.model.SeatInventoryRecord;
import com.example.demo.service.SeatInventoryService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@SecurityRequirement(name = "bearerAuth")
public class SeatInventoryController {

    private final SeatInventoryService service;

    public SeatInventoryController(SeatInventoryService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<SeatInventoryRecord> createInventory(@RequestBody SeatInventoryRecord inventory) {
        return ResponseEntity.ok(service.createInventory(inventory));
    }

    @PutMapping("/{eventId}/remaining")
    public ResponseEntity<SeatInventoryRecord> updateRemainingSeats(@PathVariable Long eventId, @RequestBody Integer remainingSeats) {
        SeatInventoryRecord inv = service.getInventoryByEvent(eventId);
        if (inv != null) {
            inv.setRemainingSeats(remainingSeats);
            return ResponseEntity.ok(service.createInventory(inv)); // Re-using create/save logic
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/event/{eventId}")
    public ResponseEntity<SeatInventoryRecord> getInventoryByEvent(@PathVariable Long eventId) {
        return ResponseEntity.ok(service.getInventoryByEvent(eventId));
    }

    @GetMapping
    public ResponseEntity<List<SeatInventoryRecord>> getAllInventories() {
        return ResponseEntity.ok(service.getAllInventories());
    }
}