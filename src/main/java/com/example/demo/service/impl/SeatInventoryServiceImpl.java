package com.example.demo.service.impl;

import com.example.demo.model.SeatInventoryRecord;
import com.example.demo.repository.SeatInventoryRecordRepository;
import com.example.demo.service.SeatInventoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeatInventoryServiceImpl implements SeatInventoryService {

    private final SeatInventoryRecordRepository repository;

    public SeatInventoryServiceImpl(SeatInventoryRecordRepository repository) {
        this.repository = repository;
    }

    @Override
    public SeatInventoryRecord create(SeatInventoryRecord record) {
        return repository.save(record);
    }

    @Override
    public SeatInventoryRecord updateRemaining(Long eventId, int remainingSeats) {
        SeatInventoryRecord record = repository.findByEventId(eventId).orElse(null);
        if (record != null) {
            record.setRemainingSeats(remainingSeats);
            return repository.save(record);
        }
        return null;
    }

    @Override
    public SeatInventoryRecord getByEvent(Long eventId) {
        return repository.findByEventId(eventId).orElse(null);
    }

    @Override
    public List<SeatInventoryRecord> getAll() {
        return repository.findAll();
    }
}
