package com.example.demo.controller;

import com.example.demo.model.PricingRule;
import com.example.demo.service.PricingRuleService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rules")
@SecurityRequirement(name = "bearerAuth")
public class PricingRuleController {

    private final PricingRuleService service;

    public PricingRuleController(PricingRuleService service) {
        this.service = service;
    }

    @PostMapping
    public PricingRule create(@RequestBody PricingRule rule) {
        return service.create(rule);
    }

    @GetMapping("/active")
    public List<PricingRule> getActiveRules() {
        return service.getActiveRules();
    }
}
