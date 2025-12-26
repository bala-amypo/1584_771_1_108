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

    public SeatInventoryRecord createInventory(SeatInventoryRecord record) {
        return repository.save(record);
    }

    public SeatInventoryRecord updateRemaining(long eventId, int remaining) {
        SeatInventoryRecord rec = repository.findByEventId(eventId).orElse(null);
        if (rec != null) {
            rec.setRemainingSeats(remaining);
            return repository.save(rec);
        }
        return null;
    }

    public SeatInventoryRecord getInventoryByEvent(long eventId) {
        return repository.findByEventId(eventId).orElse(null);
    }

    public List<SeatInventoryRecord> getAll() {
        return repository.findAll();
    }
}
