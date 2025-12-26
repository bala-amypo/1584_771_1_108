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
    public EventRecord create(@RequestBody EventRecord record) {
        return service.createEvent(record);
    }

    @GetMapping("/{id}")
    public EventRecord getById(@PathVariable Long id) {
        return service.getEventById(id)
                .orElseThrow(() -> new RuntimeException("Event not found"));
    }

    @GetMapping
    public List<EventRecord> getAll() {
        return service.getAllEvents();
    }

    @PutMapping("/{id}/status")
    public EventRecord updateStatus(
            @PathVariable Long id,
            @RequestParam boolean active) {

        return service.updateEventStatus(id, active)
                .orElseThrow(() -> new RuntimeException("Event not found"));
    }

    @GetMapping("/code/{code}")
    public EventRecord getByCode(@PathVariable String code) {
        return service.getEventByCode(code)
                .orElseThrow(() -> new RuntimeException("Event not found"));
    }
}
