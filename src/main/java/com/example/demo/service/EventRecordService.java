package com.example.demo.service;

import com.example.demo.model.EventRecord;
import java.util.List;

public interface EventRecordService {
    EventRecord create(EventRecord event);
    EventRecord getById(Long id);
    List<EventRecord> getAll();
    EventRecord updateStatus(Long id, boolean active);
    EventRecord findByCode(String eventCode);
}
