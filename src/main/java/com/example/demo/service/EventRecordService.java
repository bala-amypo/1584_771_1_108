package com.example.demo.service;

import com.example.demo.model.EventRecord;

import java.util.List;
import java.util.Optional;

public interface EventRecordService {

    EventRecord createEvent(EventRecord event);

    Optional<EventRecord> getEventById(long id);

    Optional<EventRecord> getEventByCode(String code);

    List<EventRecord> getAllEvents();

    EventRecord updateEventStatus(long id, boolean active);
}
