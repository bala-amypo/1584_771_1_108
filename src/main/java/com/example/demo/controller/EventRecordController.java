// src/main/java/com/example/demo/controller/EventRecordController.java
package com.example.demo.controller;

import com.example.demo.model.EventRecord;
import com.example.demo.service.EventRecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
@Tag(name = "Events")
public class EventRecordController {
    private final EventRecordService service;

    public EventRecordController(EventRecordService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Create event")
    public EventRecord create(@RequestBody EventRecord record) {
        return service.createEvent(record);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get event by id")
    public EventRecord getById(@PathVariable Long id) {
        return service.getEventById(id);
    }

    @GetMapping
    @Operation(summary = "Get all events")
    public List<EventRecord> getAll() {
        return service.getAllEvents();
    }

    @PutMapping("/{id}/status")
    @Operation(summary = "Update event status")
    public EventRecord updateStatus(@PathVariable Long id, @RequestParam boolean active) {
        return service.updateEventStatus(id, active);
    }

    @GetMapping("/lookup/{code}")
    @Operation(summary = "Lookup event by code")
    public EventRecord getByCode(@PathVariable String code) {
        return service.getEventByCode(code).orElse(null);
    }
}