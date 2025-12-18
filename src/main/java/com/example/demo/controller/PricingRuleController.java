package com.example.demo.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.PricingRule;
import com.example.demo.service.PricingRuleService;

@RestController
@RequestMapping("/rules")
public class PricingRuleController {

    private final PricingRuleService service;

    public PricingRuleController(PricingRuleService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<PricingRule> create(@RequestBody PricingRule rule) {
        return ResponseEntity.ok(service.createRule(rule));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PricingRule> update(@PathVariable Long id, @RequestBody PricingRule updatedRule) {
        return ResponseEntity.ok(service.updateRule(id, updatedRule));
    }

    @GetMapping("/active")
    public ResponseEntity<List<PricingRule>> getActive() {
        return ResponseEntity.ok(service.getActiveRules());
    }

    @GetMapping("/code/{ruleCode}")
    public ResponseEntity<PricingRule> getByCode(@PathVariable String ruleCode) {
        return ResponseEntity.ok(service.getRuleByCode(ruleCode));
    }

    @GetMapping
    public ResponseEntity<List<PricingRule>> getAll() {
        return ResponseEntity.ok(service.getAllRules());
    }
}
