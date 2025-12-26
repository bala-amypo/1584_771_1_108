package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "price_adjustment_logs")
public class PriceAdjustmentLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long eventId;
    private Double oldPrice;
    private Double newPrice;
    private String reason;
    private LocalDateTime changedAt;

    public PriceAdjustmentLog() {}

    public PriceAdjustmentLog(Long id, Long eventId, Double oldPrice, Double newPrice,
                              String reason, LocalDateTime changedAt) {
        this.id = id;
        this.eventId = eventId;
        this.oldPrice = oldPrice;
        this.newPrice = newPrice;
        this.reason = reason;
        this.changedAt = changedAt;
    }

    @PrePersist
    public void prePersist() {
        changedAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public Long getEventId() { return eventId; }
    public Double getOldPrice() { return oldPrice; }
    public Double getNewPrice() { return newPrice; }
    public String getReason() { return reason; }
    public LocalDateTime getChangedAt() { return changedAt; }

    public void setId(Long id) { this.id = id; }
    public void setEventId(Long eventId) { this.eventId = eventId; }
    public void setOldPrice(Double oldPrice) { this.oldPrice = oldPrice; }
    public void setNewPrice(Double newPrice) { this.newPrice = newPrice; }
    public void setReason(String reason) { this.reason = reason; }
    public void setChangedAt(LocalDateTime changedAt) { this.changedAt = changedAt; }
}
