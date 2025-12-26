package com.example.demo.controller;

import com.example.demo.model.PricingRule;
import com.example.demo.service.PricingRuleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pricing-rules")
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
    public PricingRule update(@PathVariable long id, @RequestBody PricingRule rule) {
        return service.updateRule(id, rule);
    }

    @GetMapping("/{id}")
    public PricingRule getById(@PathVariable long id) {
        return service.getById(id);
    }

    @GetMapping
    public List<PricingRule> getAll() {
        return service.getAllRules();
    }
}
