package com.example.demo.controller;

import com.example.demo.model.SeatInventoryRecord;
import com.example.demo.service.SeatInventoryService;
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
    public SeatInventoryRecord update(@PathVariable Long eventId, @RequestParam int remaining) {
        return service.updateRemaining(eventId, remaining);
    }

    @GetMapping("/{eventId}")
    public SeatInventoryRecord byEvent(@PathVariable Long eventId) {
        return service.getInventoryByEvent(eventId);
    }

    @GetMapping
    public List<SeatInventoryRecord> all() {
        return service.getAll();
    }
}
