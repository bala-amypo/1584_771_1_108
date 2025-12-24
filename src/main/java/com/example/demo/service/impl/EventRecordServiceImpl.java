package com.example.demo.service.impl;

import com.example.demo.exception.BadRequestException;
import com.example.demo.model.EventRecord;
import com.example.demo.repository.EventRecordRepository;
import com.example.demo.service.EventRecordService;
import java.util.*;

public class EventRecordServiceImpl implements EventRecordService {

    private final EventRecordRepository repo;

    public EventRecordServiceImpl(EventRecordRepository repo) {
        this.repo = repo;
    }

    public EventRecord createEvent(EventRecord e) {
        if (repo.existsByEventCode(e.getEventCode()))
            throw new BadRequestException("Event code already exists");
        if (e.getBasePrice() == null || e.getBasePrice() <= 0)
            throw new BadRequestException("Base price must be > 0");
        return repo.save(e);
    }

    public EventRecord getEventById(Long id) {
        return repo.findById(id).orElseThrow();
    }

    public Optional<EventRecord> getEventByCode(String code) {
        return repo.findByEventCode(code);
    }

    public List<EventRecord> getAllEvents() {
        return repo.findAll();
    }

    public EventRecord updateEventStatus(Long id, boolean active) {
        EventRecord e = getEventById(id);
        e.setActive(active);
        return repo.save(e);
    }
}
