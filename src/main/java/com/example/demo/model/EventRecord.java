package com.example.demo.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class EventRecord {
    private long id;
    private String eventCode;
    private String eventName;
    private String venue;
    private LocalDate eventDate;
    private Double basePrice;
    private LocalDateTime createdAt;
    private Boolean active;
    
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

    


    
}
