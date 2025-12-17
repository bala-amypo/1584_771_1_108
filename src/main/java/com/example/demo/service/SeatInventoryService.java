package com.example.demo.service;

import java.util.List;

import com.example.demo.model.SeatInventoryRecord;

public interface SeatInventoryRecordService {
    SeatInventoryRecord createInventory(SeatInventoryRecord inventory); 
    SeatInventoryRecord updateRemainingSeats(Long eventId, Integer remainingSeats); 
    SeatInventoryRecord getInventoryByEvent(Long eventId);
    List<SeatInventoryRecord> getAllInventories();
    
}
