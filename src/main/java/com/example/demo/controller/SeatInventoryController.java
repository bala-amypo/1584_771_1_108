package com.example.demo.controller;

import com.example.demo.model.SeatInventoryRecord;
import com.example.demo.service.SeatInventoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class SeatInventoryController {

    private final SeatInventoryService service;

    public SeatInventoryController(SeatInventoryService service) {
        this.service = service;
    }

    @PostMapping
    public SeatInventoryRecord create(@RequestBody SeatInventoryRecord record) {
        return service.create(record);
    }

    @PutMapping("/{eventId}/remaining")
    public SeatInventoryRecord updateRemaining(
            @PathVariable Long eventId,
            @RequestParam int remainingSeats) {
        return service.updateRemaining(eventId, remainingSeats);
    }

    @GetMapping("/event/{eventId}")
    public SeatInventoryRecord getByEvent(@PathVariable Long eventId) {
        return service.getByEvent(eventId);
    }

    @GetMapping
    public List<SeatInventoryRecord> getAll() {
        return service.getAll();
    }
}
