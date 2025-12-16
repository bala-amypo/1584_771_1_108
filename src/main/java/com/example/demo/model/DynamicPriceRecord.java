package com.example.demo.model;
import java.time.LocalDateTime;

public class DynamicPriceRecord{
    private long id;
    private long eventId;
    private Double computedPrice;
    private String appliedRuleCodes;
    private LocalDateTime computedAt;
    public DynamicPriceRecord(long eventId, Double computedPrice, String appliedRuleCodes, LocalDateTime computedAt) {
        this.eventId = eventId;
        this.computedPrice = computedPrice;
        this.appliedRuleCodes = appliedRuleCodes;
        this.computedAt = computedAt;
    }
    public void setId(long id) {
        this.id = id;
    }
    public void setEventId(long eventId) {
        this.eventId = eventId;
    }
    public void setComputedPrice(Double computedPrice) {
        this.computedPrice = computedPrice;
    }
    public void setAppliedRuleCodes(String appliedRuleCodes) {
        this.appliedRuleCodes = appliedRuleCodes;
    }
    public void setComputedAt(LocalDateTime computedAt) {
        this.computedAt = computedAt;
    }
    public long getId() {
        return id;
    }
    public long getEventId() {
        return eventId;
    }
    public Double getComputedPrice() {
        return computedPrice;
    }
    public String getAppliedRuleCodes() {
        return appliedRuleCodes;
    }
    public LocalDateTime getComputedAt() {
        return computedAt;
    }
    

    
}