package com.example.demo.service;

import com.example.demo.model.SeatInventoryRecord;

import java.util.List;
import java.util.Optional;

public interface SeatInventoryService {

    SeatInventoryRecord createInventory(SeatInventoryRecord record);

    SeatInventoryRecord updateRemaining(long eventId, int remainingSeats);

    Optional<SeatInventoryRecord> getInventoryByEvent(long eventId);

    List<SeatInventoryRecord> getAll();
}
