package com.example.demo.model;

import jakarta.persistence.*;
import java.time.*;

@Entity
public class EventRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String eventCode;
    private String eventName;
    private String venue;
    private LocalDate eventDate;
    private Double basePrice;
    private Boolean active;
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        if (active == null) active = true;
        createdAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getEventCode() { return eventCode; }
    public void setEventCode(String eventCode) { this.eventCode = eventCode; }
    public String getEventName() { return eventName; }
    public void setEventName(String eventName) { this.eventName = eventName; }
    public String getVenue() { return venue; }
    public void setVenue(String venue) { this.venue = venue; }
    public LocalDate getEventDate() { return eventDate; }
    public void setEventDate(LocalDate eventDate) { this.eventDate = eventDate; }
    public Double getBasePrice() { return basePrice; }
    public void setBasePrice(Double basePrice) { this.basePrice = basePrice; }
    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }
}
