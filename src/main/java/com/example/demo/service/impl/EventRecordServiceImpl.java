package com.example.demo.service.impl;

import com.example.demo.model.EventRecord;
import com.example.demo.repository.EventRecordRepository;
import com.example.demo.service.EventRecordService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventRecordServiceImpl implements EventRecordService {

    private final EventRecordRepository repository;

    public EventRecordServiceImpl(EventRecordRepository repository) {
        this.repository = repository;
    }

    @Override
    public EventRecord createEvent(EventRecord event) {
        return repository.save(event);
    }

    @Override
    public EventRecord getEventById(long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public EventRecord getEventByCode(String code) {
        return repository.findByEventCode(code).orElse(null);
    }

    @Override
    public List<EventRecord> getAllEvents() {
        return repository.findAll();
    }

    @Override
    public EventRecord updateEventStatus(long id, boolean active) {
        EventRecord event = getEventById(id);
        if (event != null) {
            event.setActive(active);
            return repository.save(event);
        }
        return null;
    }
}
