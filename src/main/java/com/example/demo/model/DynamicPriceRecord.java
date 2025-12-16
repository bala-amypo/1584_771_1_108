package com.example.demo.model;
import java.time.LocalDateTime;

public class DynamicPriceRecord{
    private long id;
    private long eventId;
    private Double computedPrice;
    private String appliedRuleCodes;
    private LocalDateTime computedAt;
}