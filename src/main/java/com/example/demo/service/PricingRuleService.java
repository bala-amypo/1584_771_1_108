package com.example.demo.service;

import java.util.List;

import com.example.demo.model.PricingRule;

public interface PricingRuleService {
    PricingRule createRule(PricingRule rule);
    PricingRule updateRule(Long id, PricingRule updatedRule); 
    List<PricingRule> getActiveRules(); 
    PricingRule getRuleByCode(String ruleCode); 
    List<PricingRule> getAllRules();
}
