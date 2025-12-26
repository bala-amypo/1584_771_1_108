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

    public EventRecord createEvent(EventRecord event) {
        return repository.save(event);
    }

    public EventRecord getEventById(long id) {
        return repository.findById(id).orElse(null);
    }

    public EventRecord getEventByCode(String code) {
        return repository.findByEventCode(code).orElse(null);
    }

    public List<EventRecord> getAllEvents() {
        return repository.findAll();
    }

    public EventRecord updateEventStatus(long id, boolean active) {
        EventRecord event = getEventById(id);
        if (event != null) {
            event.setActive(active);
            return repository.save(event);
        }
        return null;
    }
}
