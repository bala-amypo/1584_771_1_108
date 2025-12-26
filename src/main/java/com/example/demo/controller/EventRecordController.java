package com.example.demo.controller;

import com.example.demo.model.EventRecord;
import com.example.demo.service.EventRecordService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events")
public class EventRecordController {

    private final EventRecordService service;

    public EventRecordController(EventRecordService service) {
        this.service = service;
    }

    @PostMapping
    public EventRecord create(@RequestBody EventRecord event) {
        return service.create(event);
    }

    @GetMapping("/{id}")
    public EventRecord getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping
    public List<EventRecord> getAll() {
        return service.getAll();
    }

    @PutMapping("/{id}/status")
    public EventRecord updateStatus(
            @PathVariable Long id,
            @RequestParam boolean active) {
        return service.updateStatus(id, active);
    }

    @GetMapping("/lookup/{eventCode}")
    public EventRecord findByCode(@PathVariable String eventCode) {
        return service.findByCode(eventCode);
    }
}
