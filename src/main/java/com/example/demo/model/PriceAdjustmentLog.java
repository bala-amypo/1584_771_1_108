package com.example.demo.model;
import java.time.LocalDateTime;


public class PriceAdjustementLog{
    private long id;
    private long eventId;
    private Double oldPrice;
    private Double newPrice;
    private String reason;
    private LocalDateTime changedAt;
}