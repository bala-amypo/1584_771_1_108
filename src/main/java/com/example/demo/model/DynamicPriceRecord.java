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

    // Allow this to be nullable or default to empty string
    @Column(nullable = true)
    private String appliedRuleCodes;

    @Column(nullable = false, updatable = false)
    private LocalDateTime computedAt;

    public DynamicPriceRecord() {
        // default constructor
    }

    public DynamicPriceRecord(Long eventId, Double computedPrice, String appliedRuleCodes) {
        this.eventId = eventId;
        this.computedPrice = computedPrice;
        this.appliedRuleCodes = appliedRuleCodes == null ? "" : appliedRuleCodes;
    }

    @PrePersist
    protected void onCompute() {
        this.computedAt = LocalDateTime.now();
        if (this.appliedRuleCodes == null) {
            this.appliedRuleCodes = "";
        }
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public Double getComputedPrice() {
        return computedPrice;
    }

    public void setComputedPrice(Double computedPrice) {
        this.computedPrice = computedPrice;
    }

    public String getAppliedRuleCodes() {
        return appliedRuleCodes;
    }

    public void setAppliedRuleCodes(String appliedRuleCodes) {
        this.appliedRuleCodes = appliedRuleCodes == null ? "" : appliedRuleCodes;
    }

    public LocalDateTime getComputedAt() {
        return computedAt;
    }
}
