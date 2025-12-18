package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.DynamicPriceRecord;

public interface DynamicPriceRecordRepository extends JpaRepository<DynamicPriceRecord, Long> {
    List<DynamicPriceRecord> findByEventIdOrderByTimestampDesc(Long eventId);
    DynamicPriceRecord findTopByEventIdOrderByTimestampDesc(Long eventId);
}
