package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "seat_inventory_records")
public class SeatInventoryRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Reference to EventRecord (foreign key)
    @Column(nullable = false)
    private Long eventId;

    @Column(nullable = false)
    private Integer totalSeats;

    @Column(nullable = false)
    private Integer remainingSeats;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    // Default constructor required by JPA
    public SeatInventoryRecord() {}

    // Convenience constructor (without updatedAt, since it's auto-set)
    public SeatInventoryRecord(Long eventId, Integer totalSeats, Integer remainingSeats) {
        this.eventId = eventId;
        this.totalSeats = totalSeats;
        this.remainingSeats = remainingSeats;
    }

    // Lifecycle hooks to auto-set updatedAt and validate seat counts
    @PrePersist
    @PreUpdate
    protected void validateAndUpdate() {
        if (remainingSeats != null && totalSeats != null && remainingSeats > totalSeats) {
            throw new IllegalArgumentException("Remaining seats cannot exceed total seats");
        }
        this.updatedAt = LocalDateTime.now();
    }

    // Getters
    public Long getId() {
        return id;
    }

    public Long getEventId() {
        return eventId;
    }

    public Integer getTotalSeats() {
        return totalSeats;
    }

    public Integer getRemainingSeats() {
        return remainingSeats;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public void setTotalSeats(Integer totalSeats) {
        this.totalSeats = totalSeats;
    }

    public void setRemainingSeats(Integer remainingSeats) {
        this.remainingSeats = remainingSeats;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
