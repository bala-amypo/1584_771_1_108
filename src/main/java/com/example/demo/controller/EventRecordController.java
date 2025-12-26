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
    public EventRecord getById(@PathVariable long id) {
        return service.getEventById(id);
    }

    @GetMapping
    public List<EventRecord> getAll() {
        return service.getAllEvents();
    }

    @PutMapping("/{id}/status")
    public EventRecord updateStatus(@PathVariable long id, @RequestParam boolean active) {
        return service.updateEventStatus(id, active);
    }

    @GetMapping("/code/{code}")
    public EventRecord findByCode(@PathVariable String code) {
        return service.getEventByCode(code);
    }
}
