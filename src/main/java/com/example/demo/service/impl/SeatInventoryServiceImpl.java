// src/main/java/com/example/demo/service/impl/SeatInventoryServiceImpl.java
package com.example.demo.service.impl;

import com.example.demo.exception.BadRequestException;
import com.example.demo.model.EventRecord;
import com.example.demo.model.SeatInventoryRecord;
import com.example.demo.repository.EventRecordRepository;
import com.example.demo.repository.SeatInventoryRecordRepository;
import com.example.demo.service.SeatInventoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SeatInventoryServiceImpl implements SeatInventoryService {
    private final SeatInventoryRecordRepository inventoryRepository;
    private final EventRecordRepository eventRepository;

    public SeatInventoryServiceImpl(SeatInventoryRecordRepository inventoryRepository,
                                    EventRecordRepository eventRepository) {
        this.inventoryRepository = inventoryRepository;
        this.eventRepository = eventRepository;
    }

    @Override
    public SeatInventoryRecord createInventory(SeatInventoryRecord inventory) {
        Optional<EventRecord> evt = eventRepository.findById(inventory.getEventId());
        if (evt.isEmpty()) throw new RuntimeException("Event not found");
        if (inventory.getRemainingSeats() != null && inventory.getTotalSeats() != null
                && inventory.getRemainingSeats() > inventory.getTotalSeats())
            throw new BadRequestException("Remaining seats cannot exceed total seats");
        return inventoryRepository.save(inventory);
    }

    @Override
    public SeatInventoryRecord updateRemainingSeats(Long eventId, Integer remainingSeats) {
        Optional<SeatInventoryRecord> opt = inventoryRepository.findByEventId(eventId);
        if (opt.isEmpty()) return null;
        SeatInventoryRecord inv = opt.get();
        if (remainingSeats > inv.getTotalSeats())
            throw new BadRequestException("Remaining seats cannot exceed total seats");
        inv.setRemainingSeats(remainingSeats);
        return inventoryRepository.save(inv);
    }

    @Override
    public Optional<SeatInventoryRecord> getInventoryByEvent(Long eventId) {
        return inventoryRepository.findByEventId(eventId);
    }

    @Override
    public List<SeatInventoryRecord> getAllInventories() {
        return inventoryRepository.findAll();
    }
}