// src/main/java/com/example/demo/controller/PricingRuleController.java
package com.example.demo.controller;

import com.example.demo.model.PricingRule;
import com.example.demo.service.PricingRuleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pricing-rules")
@Tag(name = "Pricing Rules")
public class PricingRuleController {
    private final PricingRuleService service;

    public PricingRuleController(PricingRuleService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Create rule")
    public PricingRule create(@RequestBody PricingRule rule) {
        return service.createRule(rule);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update rule")
    public PricingRule update(@PathVariable Long id, @RequestBody PricingRule rule) {
        return service.updateRule(id, rule);
    }

    @GetMapping("/active")
    @Operation(summary = "Get active rules")
    public List<PricingRule> active() {
        return service.getActiveRules();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get rule by id")
    public PricingRule getById(@PathVariable Long id) {
        return service.getAllRules().stream().filter(r -> r.getId().equals(id)).findFirst().orElse(null);
    }

    @GetMapping
    @Operation(summary = "Get all rules")
    public List<PricingRule> all() {
        return service.getAllRules();
    }
}