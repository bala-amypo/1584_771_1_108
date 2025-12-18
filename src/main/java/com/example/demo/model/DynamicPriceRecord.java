package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "dynamic_price_records")
public class DynamicPriceRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long eventId;

    @Column(nullable = false)
    private Double computedPrice;

    @Column(nullable = false)
    private String appliedRuleCodes;

    @Column(nullable = false)
    private LocalDateTime computedAt;

    public DynamicPriceRecord() {}

    public DynamicPriceRecord(Long eventId, Double computedPrice, String appliedRuleCodes) {
        this.eventId = eventId;
        this.computedPrice = computedPrice;
        this.appliedRuleCodes = appliedRuleCodes;
    }

    @PrePersist
    protected void validateAndComputeTimestamp() {
        if (computedPrice == null || computedPrice <= 0) {
            throw new IllegalArgumentException("computedPrice must be greater than 0");
        }
        this.computedAt = LocalDateTime.now();
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
