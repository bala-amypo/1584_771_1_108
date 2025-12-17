package com.example.demo.service;

import java.util.List;

import com.example.demo.model.EventRecord;

public interface EventRecordService {
    EventRecord createEvent(EventRecord event);
    EventRecord getEventById(Long id);
    EventRecord getEventByCode(String eventCode);
    List<EventRecord> getAllEvents();
    EventRecord updateEventStatus(Long id, boolean active);
}
