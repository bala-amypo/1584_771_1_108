package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "seat_inventory_records")
public class SeatInventoryRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long eventId;
    private Integer totalSeats;
    private Integer remainingSeats;
    private LocalDateTime updatedAt;

    public SeatInventoryRecord() {}

    public SeatInventoryRecord(Long id, Long eventId, Integer totalSeats,
                               Integer remainingSeats, LocalDateTime updatedAt) {
        this.id = id;
        this.eventId = eventId;
        this.totalSeats = totalSeats;
        this.remainingSeats = remainingSeats;
        this.updatedAt = updatedAt;
    }

    @PrePersist
    public void prePersist() {
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public Long getEventId() { return eventId; }
    public Integer getTotalSeats() { return totalSeats; }
    public Integer getRemainingSeats() { return remainingSeats; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    public void setId(Long id) { this.id = id; }
    public void setEventId(Long eventId) { this.eventId = eventId; }
    public void setTotalSeats(Integer totalSeats) { this.totalSeats = totalSeats; }
    public void setRemainingSeats(Integer remainingSeats) { this.remainingSeats = remainingSeats; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
