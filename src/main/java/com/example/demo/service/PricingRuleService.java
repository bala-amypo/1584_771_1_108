package com.example.demo.service;

import com.example.demo.model.PricingRule;
import java.util.List;

public interface PricingRuleService {

    PricingRule createRule(PricingRule rule);

    PricingRule updateRule(Long id, PricingRule updatedRule);

    PricingRule getRuleByCode(String ruleCode);

    List<PricingRule> getActiveRules();

    List<PricingRule> getAllRules();
}
