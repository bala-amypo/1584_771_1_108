package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "pricing_rules", uniqueConstraints = @UniqueConstraint(columnNames = "ruleCode"))
public class PricingRule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String ruleCode;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Integer minRemainingSeats;

    @Column(nullable = false)
    private Integer maxRemainingSeats;

    @Column(nullable = false)
    private Integer daysBeforeEvent;

    @Column(nullable = false)
    private Double priceMultiplier;

    @Column(nullable = false)
    private Boolean active;

    public PricingRule() {}

    public boolean appliesTo(Integer remainingSeats, LocalDate eventDate) {
        return active &&
               remainingSeats >= minRemainingSeats &&
               remainingSeats <= maxRemainingSeats &&
               eventDate != null &&
               java.time.LocalDate.now().until(eventDate).getDays() >= daysBeforeEvent;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getRuleCode() {
        return ruleCode;
    }

    public String getDescription() {
        return description;
    }

    public Integer getMinRemainingSeats() {
        return minRemainingSeats;
    }

    public Integer getMaxRemainingSeats() {
        return maxRemainingSeats;
    }

    public Integer getDaysBeforeEvent() {
        return daysBeforeEvent;
    }

    public Double getPriceMultiplier() {
        return priceMultiplier;
    }

    public Boolean getActive() {
        return active;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setRuleCode(String ruleCode) {
        this.ruleCode = ruleCode;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setMinRemainingSeats(Integer minRemainingSeats) {
        this.minRemainingSeats = minRemainingSeats;
    }

    public void setMaxRemainingSeats(Integer maxRemainingSeats) {
        this.maxRemainingSeats = maxRemainingSeats;
    }

    public void setDaysBeforeEvent(Integer daysBeforeEvent) {
        this.daysBeforeEvent = daysBeforeEvent;
    }

    public void setPriceMultiplier(Double priceMultiplier) {
        this.priceMultiplier = priceMultiplier;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
