package com.example.demo.service;

import com.example.demo.model.PricingRule;

import java.util.List;
import java.util.Optional;

public interface PricingRuleService {

    PricingRule createRule(PricingRule rule);

    PricingRule updateRule(Long id, PricingRule rule);

    Optional<PricingRule> getRuleById(Long id);

    List<PricingRule> getAllRules();

    List<PricingRule> getActiveRules();
}
