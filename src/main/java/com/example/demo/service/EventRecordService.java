package com.example.demo.service;

import com.example.demo.model.EventRecord;

import java.util.List;

public interface EventRecordService {

    EventRecord createEvent(EventRecord event);

    EventRecord getEventById(long id);

    EventRecord getEventByCode(String code);

    List<EventRecord> getAllEvents();

    EventRecord updateEventStatus(long id, boolean active);
}
