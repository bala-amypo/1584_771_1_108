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
    public ResponseEntity<PricingRule> create(@RequestBody PricingRule rule) {
        return ResponseEntity.ok(service.create(rule));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PricingRule> update(
            @PathVariable Long id,
            @RequestBody PricingRule rule) {
        return ResponseEntity.ok(service.update(id, rule));
    }

    @GetMapping("/active")
    public ResponseEntity<List<PricingRule>> getActive() {
        return ResponseEntity.ok(service.getActiveRules());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PricingRule> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    // GET /api/pricing-rules – List all
    @GetMapping
    public ResponseEntity<List<PricingRule>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }
}
