package com.example.demo.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.model.EventRecord;
import com.example.demo.repository.EventRecordRepository;
import com.example.demo.service.EventRecordService;

@Service
public class EventRecordServiceImpl implements EventRecordService {

    private final EventRecordRepository repository;

    public EventRecordServiceImpl(EventRecordRepository repository) {
        this.repository = repository;
    }

    @Override
    public EventRecord createEvent(EventRecord event) {
        if (repository.existsByEventCode(event.getEventCode())) {
            throw new IllegalArgumentException("Duplicate event code");
        }
        return repository.save(event);
    }

    @Override
    public EventRecord getEventById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public EventRecord getEventByCode(String eventCode) {
        return repository.findByEventCode(eventCode);
    }

    @Override
    public List<EventRecord> getAllEvents() {
        return repository.findAll();
    }

    @Override
    public EventRecord updateEventStatus(Long id, boolean active) {
        EventRecord event = repository.findById(id).orElse(null);
        if (event != null) {
            event.setActive(active);
            repository.save(event);
        }
        return event;
    }
}
