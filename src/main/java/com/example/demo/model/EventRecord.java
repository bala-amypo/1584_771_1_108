package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "event_records", uniqueConstraints = @UniqueConstraint(columnNames = "eventCode"))
public class EventRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true)
    private String eventCode;

    @Column(nullable = false)
    private String eventName;

    @Column(nullable = false)
    private String venue;

    @Column(nullable = false)
    private LocalDate eventDate;

    @Column(nullable = false)
    private Double basePrice;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private Boolean active;

    public EventRecord() {}

    public EventRecord(String eventCode, String eventName, String venue, LocalDate eventDate, Double basePrice,
                       LocalDateTime createdAt, Boolean active) {
        this.eventCode = eventCode;
        this.eventName = eventName;
        this.venue = venue;
        this.eventDate = eventDate;
        this.basePrice = basePrice;
        this.createdAt = createdAt;
        this.active = active;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    // Getters and setters...

    public long getId() {
        return id;
    }

    public String getEventCode() {
        return eventCode;
    }

    public String getEventName() {
        return eventName;
    }

    public String getVenue() {
        return venue;
    }

    public LocalDate getEventDate() {
        return eventDate;
    }

    public Double getBasePrice() {
        return basePrice;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Boolean getActive() {
        return active;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setEventCode(String eventCode) {
        this.eventCode = eventCode;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public void setEventDate(LocalDate eventDate) {
        this.eventDate = eventDate;
    }

    public void setBasePrice(Double basePrice) {
        this.basePrice = basePrice;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
