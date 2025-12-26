package com.example.demo.controller;

import com.example.demo.model.PricingRule;
import com.example.demo.service.PricingRuleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pricing-rules")
public class PricingRuleController {

    private final PricingRuleService service;

    public PricingRuleController(PricingRuleService service) {
        this.service = service;
    }

    @PostMapping
    public PricingRule create(@RequestBody PricingRule rule) {
        return service.create(rule);
    }

    @PutMapping("/{id}")
    public PricingRule update(@PathVariable Long id, @RequestBody PricingRule rule) {
        return service.update(id, rule);
    }

    @GetMapping("/{id}")
    public PricingRule getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping("/active")
    public List<PricingRule> getActive() {
        return service.getActiveRules();
    }

    @GetMapping
    public List<PricingRule> getAll() {
        return service.getAll();
    }
}
