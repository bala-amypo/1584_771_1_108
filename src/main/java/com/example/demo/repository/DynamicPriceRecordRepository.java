package com.example.demo.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.model.DynamicPriceRecord;

public interface DynamicPriceRecordRepository extends JpaRepository<DynamicPriceRecord, Long> {
    List<DynamicPriceRecord> findByEventIdOrderByComputedAtDesc(Long eventId);
    DynamicPriceRecord findTopByEventIdOrderByComputedAtDesc(Long eventId);
}
