package com.example.demo.service.impl;

import com.example.demo.model.EventRecord;
import com.example.demo.repository.EventRecordRepository;
import com.example.demo.service.EventRecordService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventRecordServiceImpl implements EventRecordService {

    private final EventRecordRepository eventRepo;

    public EventRecordServiceImpl(EventRecordRepository eventRepo) {
        this.eventRepo = eventRepo;
    }

    @Override
    public EventRecord createEvent(EventRecord event) {
        return eventRepo.save(event);
    }

    @Override
    public Optional<EventRecord> getEventById(long id) {
        return eventRepo.findById(id);
    }

    @Override
    public Optional<EventRecord> getEventByCode(String code) {
        return eventRepo.findByEventCode(code);
    }

    @Override
    public List<EventRecord> getAllEvents() {
        return eventRepo.findAll();
    }

    @Override
    public EventRecord updateEventStatus(long id, boolean active) {
        EventRecord event = eventRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found"));
        event.setActive(active);
        return eventRepo.save(event);
    }
}
