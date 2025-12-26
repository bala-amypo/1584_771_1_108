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
        return service.createEvent(event);
    }

    @GetMapping("/{id}")
    public EventRecord get(@PathVariable Long id) {
        return service.getEventById(id);
    }

    @GetMapping
    public List<EventRecord> all() {
        return service.getAllEvents();
    }

    @PutMapping("/{id}/status")
    public EventRecord update(@PathVariable Long id, @RequestParam boolean active) {
        return service.updateEventStatus(id, active);
    }

    @GetMapping("/code/{code}")
    public EventRecord byCode(@PathVariable String code) {
        return service.getEventByCode(code);
    }
}
