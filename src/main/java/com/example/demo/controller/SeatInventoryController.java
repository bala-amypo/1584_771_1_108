package com.example.demo.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import com.example.demo.model.SeatInventoryRecord;
import com.example.demo.service.SeatInventoryService;

@RestController
@RequestMapping("/inventories")
@SecurityRequirement(name = "bearerAuth")

public class SeatInventoryController {

    private final SeatInventoryService service;

    public SeatInventoryController(SeatInventoryService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<SeatInventoryRecord> create(@RequestBody SeatInventoryRecord inventory) {
        return ResponseEntity.ok(service.createInventory(inventory));
    }

    @PutMapping("/{eventId}/remaining")
    public ResponseEntity<SeatInventoryRecord> updateRemaining(@PathVariable Long eventId,
                                                               @RequestParam Integer remainingSeats) {
        SeatInventoryRecord updated = service.updateRemainingSeats(eventId, remainingSeats);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<SeatInventoryRecord> getByEvent(@PathVariable Long eventId) {
        SeatInventoryRecord record = service.getInventoryByEvent(eventId);
        return record != null ? ResponseEntity.ok(record) : ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<SeatInventoryRecord>> getAll() {
        return ResponseEntity.ok(service.getAllInventories());
    }
}
