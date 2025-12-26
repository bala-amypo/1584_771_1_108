package com.example.demo.service;

import com.example.demo.model.SeatInventoryRecord;
import java.util.List;

public interface SeatInventoryService {

    SeatInventoryRecord createInventory(SeatInventoryRecord record);

    SeatInventoryRecord updateRemaining(long eventId, int remaining);

    SeatInventoryRecord getInventoryByEvent(long eventId);

    List<SeatInventoryRecord> getAll();
}
