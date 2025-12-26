package com.example.demo.service.impl;

import com.example.demo.model.SeatInventoryRecord;
import com.example.demo.repository.EventRecordRepository;
import com.example.demo.repository.SeatInventoryRecordRepository;
import com.example.demo.service.SeatInventoryService;
import org.springframework.stereotype.Service;

@Service
public class SeatInventoryServiceImpl implements SeatInventoryService {

    private final SeatInventoryRecordRepository repository;

    // REQUIRED constructor (exact test match)
    public SeatInventoryServiceImpl(
            SeatInventoryRecordRepository repository,
            EventRecordRepository eventRecordRepository
    ) {
        this.repository = repository;
    }

    @Override
    public SeatInventoryRecord createInventory(SeatInventoryRecord record) {
        return repository.save(record);
    }

    @Override
    public SeatInventoryRecord getInventoryByEvent(long eventId) {
        return repository.findByEventId(eventId).orElse(null);
    }
}
