package com.example.demo.controller;

import com.example.demo.model.PricingRule;
import com.example.demo.service.PricingRuleService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pricing-rules")
@SecurityRequirement(name = "bearerAuth")
public class PricingRuleController {

    private final PricingRuleService service;

    public PricingRuleController(PricingRuleService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<PricingRule> createRule(@RequestBody PricingRule rule) {
        return ResponseEntity.ok(service.createRule(rule));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PricingRule> updateRule(@PathVariable Long id, @RequestBody PricingRule rule) {
        // Note: Logic to find and update would go here or in service
        // Assuming creation logic for update in this simplified version
        rule.setId(id);
        return ResponseEntity.ok(service.createRule(rule));
    }

    @GetMapping("/active")
    public ResponseEntity<List<PricingRule>> getActiveRules() {
        return ResponseEntity.ok(service.getActiveRules());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PricingRule> getRuleById(@PathVariable Long id) {
        // Simplified: Fetch from all list or add getById to Service
        return service.getAllRules().stream()
                .filter(r -> r.getId().equals(id))
                .findFirst()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<PricingRule>> getAllRules() {
        return ResponseEntity.ok(service.getAllRules());
    }
}