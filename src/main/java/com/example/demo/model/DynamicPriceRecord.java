package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "dynamic_price_records")
public class DynamicPriceRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long eventId;
    private Double computedPrice;
    private String appliedRuleCodes;
    private LocalDateTime computedAt;

    public DynamicPriceRecord() {}

    public DynamicPriceRecord(Long id, Long eventId, Double computedPrice,
                              String appliedRuleCodes, LocalDateTime computedAt) {
        this.id = id;
        this.eventId = eventId;
        this.computedPrice = computedPrice;
        this.appliedRuleCodes = appliedRuleCodes;
        this.computedAt = computedAt;
    }

    @PrePersist
    public void prePersist() {
        computedAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public Long getEventId() { return eventId; }
    public Double getComputedPrice() { return computedPrice; }
    public String getAppliedRuleCodes() { return appliedRuleCodes; }
    public LocalDateTime getComputedAt() { return computedAt; }

    public void setId(Long id) { this.id = id; }
    public void setEventId(Long eventId) { this.eventId = eventId; }
    public void setComputedPrice(Double computedPrice) { this.computedPrice = computedPrice; }
    public void setAppliedRuleCodes(String appliedRuleCodes) { this.appliedRuleCodes = appliedRuleCodes; }
    public void setComputedAt(LocalDateTime computedAt) { this.computedAt = computedAt; }
}
