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

import com.example.demo.model.EventRecord;
import com.example.demo.service.EventRecordService;

@RestController
@RequestMapping("/events")
public class EventRecordController {

    private final EventRecordService service;

    public EventRecordController(EventRecordService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<EventRecord> create(@RequestBody EventRecord event) {
        return ResponseEntity.ok(service.createEvent(event));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventRecord> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getEventById(id));
    }

    @GetMapping("/code/{eventCode}")
    public ResponseEntity<EventRecord> getByCode(@PathVariable String eventCode) {
        return ResponseEntity.ok(service.getEventByCode(eventCode));
    }

    @GetMapping
    public ResponseEntity<List<EventRecord>> getAll() {
        return ResponseEntity.ok(service.getAllEvents());
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<EventRecord> updateStatus(@PathVariable Long id, @RequestParam boolean active) {
        return ResponseEntity.ok(service.updateEventStatus(id, active));
    }
}
