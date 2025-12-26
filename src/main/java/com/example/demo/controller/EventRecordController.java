package com.example.demo.controller;

import com.example.demo.model.EventRecord;
import com.example.demo.service.EventRecordService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/events")
@SecurityRequirement(name = "bearerAuth")
public class EventRecordController {

    private final EventRecordService service;

    public EventRecordController(EventRecordService service) {
        this.service = service;
    }

    @PostMapping
    public EventRecord createEvent(@RequestBody EventRecord event) {
        return service.createEvent(event);
    }

    @GetMapping
    public List<EventRecord> getAllEvents() {
        return service.getAllEvents();
    }

    @GetMapping("/{id}")
    public EventRecord getEventById(@PathVariable Long id) {
        return service.getEventById(id);
    }

    @GetMapping("/code/{code}")
    public Optional<EventRecord> getByCode(@PathVariable String code) {
        return service.getEventByCode(code);
    }

    @PutMapping("/{id}/status")
    public EventRecord updateStatus(
            @PathVariable Long id,
            @RequestParam boolean active
    ) {
        return service.updateEventStatus(id, active);
    }
}
