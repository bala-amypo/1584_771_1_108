package com.example.demo.service;

import com.example.demo.model.SeatInventoryRecord;
import java.util.List;

public interface SeatInventoryService {
    SeatInventoryRecord create(SeatInventoryRecord record);
    SeatInventoryRecord updateRemaining(Long eventId, int remainingSeats);
    SeatInventoryRecord getByEvent(Long eventId);
    List<SeatInventoryRecord> getAll();
}
