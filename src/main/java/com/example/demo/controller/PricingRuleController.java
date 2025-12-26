package com.example.demo.controller;

import com.example.demo.model.PricingRule;
import com.example.demo.service.PricingRuleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rules")
public class PricingRuleController {

    private final PricingRuleService service;

    public PricingRuleController(PricingRuleService service) {
        this.service = service;
    }

    @PostMapping
    public PricingRule create(@RequestBody PricingRule rule) {
        return service.createRule(rule);
    }

    @PutMapping("/{id}")
    public PricingRule update(@PathVariable Long id, @RequestBody PricingRule rule) {
        return service.updateRule(id, rule);
    }

    @GetMapping("/{id}")
    public PricingRule get(@PathVariable Long id) {
        return service.getRuleById(id);
    }

    @GetMapping("/active")
    public List<PricingRule> active() {
        return service.getActiveRules();
    }

    @GetMapping
    public List<PricingRule> all() {
        return service.getAllRules();
    }
}
