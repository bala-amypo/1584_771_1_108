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
    public ResponseEntity<SeatInventoryRecord> create(
            @RequestBody SeatInventoryRecord record) {
        return ResponseEntity.ok(service.create(record));
    }

    @PutMapping("/{eventId}/remaining")
    public ResponseEntity<SeatInventoryRecord> updateRemaining(
            @PathVariable Long eventId,
            @RequestParam int remainingSeats) {
        return ResponseEntity.ok(service.updateRemaining(eventId, remainingSeats));
    }

    @GetMapping("/event/{eventId}")
    public ResponseEntity<SeatInventoryRecord> getByEvent(
            @PathVariable Long eventId) {
        return ResponseEntity.ok(service.getByEvent(eventId));
    }

    @GetMapping
    public ResponseEntity<List<SeatInventoryRecord>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }
}
