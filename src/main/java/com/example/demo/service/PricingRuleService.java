package com.example.demo.service;

import com.example.demo.model.PricingRule;
import java.util.List;

public interface PricingRuleService {

    PricingRule createRule(PricingRule rule);

    PricingRule updateRule(long id, PricingRule rule);

    PricingRule getById(long id);

    List<PricingRule> getAllRules();
}
