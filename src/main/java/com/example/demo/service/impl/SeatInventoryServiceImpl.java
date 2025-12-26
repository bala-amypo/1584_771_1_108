package com.example.demo.service.impl;

import com.example.demo.exception.BadRequestException;
import com.example.demo.model.SeatInventoryRecord;
import com.example.demo.repository.EventRecordRepository;
import com.example.demo.repository.SeatInventoryRecordRepository;
import com.example.demo.service.SeatInventoryService;

import java.util.List;

public class SeatInventoryServiceImpl implements SeatInventoryService {

    private final SeatInventoryRecordRepository inventoryRepo;
    private final EventRecordRepository eventRepo;

    public SeatInventoryServiceImpl(
            SeatInventoryRecordRepository inventoryRepo,
            EventRecordRepository eventRepo) {
        this.inventoryRepo = inventoryRepo;
        this.eventRepo = eventRepo;
    }

    @Override
    public SeatInventoryRecord createInventory(SeatInventoryRecord inv) {

        eventRepo.findById(inv.getEventId()).orElseThrow();

        if (inv.getRemainingSeats() > inv.getTotalSeats())
            throw new BadRequestException(
                    "Remaining seats cannot exceed total seats");

        return inventoryRepo.save(inv);
    }

    @Override
    public SeatInventoryRecord updateRemainingSeats(
            Long eventId, Integer remainingSeats) {

        SeatInventoryRecord inv = inventoryRepo
                .findByEventId(eventId)
                .orElseThrow();

        inv.setRemainingSeats(remainingSeats);
        return inventoryRepo.save(inv);
    }

    @Override
    public SeatInventoryRecord getInventoryByEvent(Long eventId) {
        return inventoryRepo.findByEventId(eventId).orElseThrow();
    }

    @Override
    public List<SeatInventoryRecord> getAllInventories() {
        return inventoryRepo.findAll();
    }
}
