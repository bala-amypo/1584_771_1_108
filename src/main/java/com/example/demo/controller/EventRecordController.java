package com.example.demo.controller;

import com.example.demo.model.EventRecord;
import com.example.demo.service.EventRecordService;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<EventRecord> getById(@PathVariable Long id) {
        return service.getEventById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<EventRecord> getAll() {
        return service.getAllEvents();
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<EventRecord> updateStatus(
            @PathVariable Long id,
            @RequestParam boolean active) {

        return service.updateEventStatus(id, active)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<EventRecord> getByCode(@PathVariable String code) {
        return service.getEventByCode(code)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
