package com.example.demo.service.impl;

import com.example.demo.model.EventRecord;
import com.example.demo.model.SeatInventoryRecord;
import com.example.demo.repository.EventRecordRepository;
import com.example.demo.repository.SeatInventoryRecordRepository;
import com.example.demo.service.SeatInventoryService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class SeatInventoryServiceImpl implements SeatInventoryService {

    private final SeatInventoryRecordRepository seatRepo;
    private final EventRecordRepository eventRepo;

    // ⚠ REQUIRED by test – DO NOT CHANGE
    public SeatInventoryServiceImpl(SeatInventoryRecordRepository seatRepo,
                                    EventRecordRepository eventRepo) {
        this.seatRepo = seatRepo;
        this.eventRepo = eventRepo;
    }

    @Override
    public SeatInventoryRecord createInventory(SeatInventoryRecord record) {
        record.setUpdatedAt(LocalDateTime.now());
        return seatRepo.save(record);
    }

    @Override
    public SeatInventoryRecord updateRemaining(long eventId, int remainingSeats) {
        SeatInventoryRecord record = seatRepo.findByEventId(eventId)
                .orElseThrow(() -> new RuntimeException("Inventory not found"));
        record.setRemainingSeats(remainingSeats);
        record.setUpdatedAt(LocalDateTime.now());
        return seatRepo.save(record);
    }

    @Override
    public Optional<SeatInventoryRecord> getInventoryByEvent(long eventId) {
        return seatRepo.findByEventId(eventId);
    }

    @Override
    public List<SeatInventoryRecord> getAll() {
        return seatRepo.findAll();
    }
}
