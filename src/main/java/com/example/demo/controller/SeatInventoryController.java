package com.example.demo.controller;

import com.example.demo.model.SeatInventoryRecord;
import com.example.demo.service.SeatInventoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventory")
public class SeatInventoryController {

    private final SeatInventoryService service;

    public SeatInventoryController(SeatInventoryService service) {
        this.service = service;
    }

    @PostMapping
    public SeatInventoryRecord create(@RequestBody SeatInventoryRecord record) {
        return service.createInventory(record);
    }

    @PutMapping("/{eventId}")
    public ResponseEntity<SeatInventoryRecord> updateRemaining(
            @PathVariable Long eventId,
            @RequestParam int remaining) {

        return service.updateRemaining(eventId, remaining)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<SeatInventoryRecord> getByEvent(@PathVariable Long eventId) {
        return service.getInventoryByEvent(eventId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<SeatInventoryRecord> getAll() {
        return service.getAll();
    }
}
