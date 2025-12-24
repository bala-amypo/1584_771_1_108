package com.example.demo.repository;

import com.example.demo.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

public interface EventRecordRepository extends JpaRepository<EventRecord, Long> {
    boolean existsByEventCode(String code);
    Optional<EventRecord> findByEventCode(String code);
}

public interface SeatInventoryRecordRepository extends JpaRepository<SeatInventoryRecord, Long> {
    Optional<SeatInventoryRecord> findByEventId(Long eventId);
}

public interface PricingRuleRepository extends JpaRepository<PricingRule, Long> {
    boolean existsByRuleCode(String code);
    List<PricingRule> findByActiveTrue();
}

public interface DynamicPriceRecordRepository extends JpaRepository<DynamicPriceRecord, Long> {
    List<DynamicPriceRecord> findByEventIdOrderByComputedAtDesc(Long eventId);
    Optional<DynamicPriceRecord> findFirstByEventIdOrderByComputedAtDesc(Long eventId);
}

public interface PriceAdjustmentLogRepository extends JpaRepository<PriceAdjustmentLog, Long> {
    List<PriceAdjustmentLog> findByEventId(Long eventId);
}
