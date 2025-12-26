package com.example.demo.controller;

import com.example.demo.model.EventRecord;
import com.example.demo.service.EventRecordService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
@SecurityRequirement(name = "bearerAuth")
public class EventRecordController {

    private final EventRecordService service;

    public EventRecordController(EventRecordService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<EventRecord> createEvent(@RequestBody EventRecord event) {
        return ResponseEntity.ok(service.createEvent(event));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventRecord> getEventById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getEventById(id));
    }

    @GetMapping
    public ResponseEntity<List<EventRecord>> getAllEvents() {
        return ResponseEntity.ok(service.getAllEvents());
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<EventRecord> updateEventStatus(@PathVariable Long id, @RequestBody Boolean active) {
        return ResponseEntity.ok(service.updateEventStatus(id, active));
    }

    @GetMapping("/lookup/{eventCode}")
    public ResponseEntity<EventRecord> getEventByCode(@PathVariable String eventCode) {
        return ResponseEntity.of(service.getEventByCode(eventCode));
    }
}