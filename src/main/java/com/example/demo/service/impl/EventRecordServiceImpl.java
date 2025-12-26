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
    public EventRecord create(EventRecord event) {
        return repository.save(event);
    }

    @Override
    public EventRecord getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<EventRecord> getAll() {
        return repository.findAll();
    }

    @Override
    public EventRecord updateStatus(Long id, boolean active) {
        EventRecord event = getById(id);
        if (event != null) {
            event.setActive(active);
            return repository.save(event);
        }
        return null;
    }

    @Override
    public EventRecord findByCode(String eventCode) {
        return repository.findByEventCode(eventCode).orElse(null);
    }
}
