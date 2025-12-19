package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.SeatInventoryRecord;

public interface SeatInventoryRecordRepository extends JpaRepository<SeatInventoryRecord, Long> {
    SeatInventoryRecord findByEventId(Long eventId);
}
