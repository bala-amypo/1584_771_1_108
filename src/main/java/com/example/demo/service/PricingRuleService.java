package com.example.demo.service;

import com.example.demo.model.PricingRule;
import java.util.List;

public interface PricingRuleService {
    PricingRule create(PricingRule rule);
    PricingRule update(Long id, PricingRule rule);
    PricingRule getById(Long id);
    List<PricingRule> getActiveRules();
    List<PricingRule> getAll();
}
