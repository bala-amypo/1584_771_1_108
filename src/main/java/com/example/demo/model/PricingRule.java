package com.example.demo.model;

import jakarta.persistence.*;

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

    public PricingRule(String ruleCode, String description, Integer minRemainingSeats, Integer maxRemainingSeats,
                       Integer daysBeforeEvent, Double priceMultiplier, Boolean active) {
        this.ruleCode = ruleCode;
        this.description = description;
        this.minRemainingSeats = minRemainingSeats;
        this.maxRemainingSeats = maxRemainingSeats;
        this.daysBeforeEvent = daysBeforeEvent;
        this.priceMultiplier = priceMultiplier;
        this.active = active;
    }

    @PrePersist
    @PreUpdate
    protected void validateMultiplier() {
        if (priceMultiplier == null || priceMultiplier <= 0) {
            throw new IllegalArgumentException("priceMultiplier must be greater than 0");
        }
    }

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
