package com.example.demo.repository;

import com.example.demo.model.PriceAdjustmentLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PriceAdjustmentLogRepository extends JpaRepository<PriceAdjustmentLog, Long> {

    List<PriceAdjustmentLog> findByEventId(Long eventId);

    Optional<PriceAdjustmentLog> findTopByEventIdOrderByChangedAtDesc(Long eventId);
}
