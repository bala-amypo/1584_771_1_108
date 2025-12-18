package com.example.demo.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.model.PricingRule;
import com.example.demo.repository.PricingRuleRepository;
import com.example.demo.service.PricingRuleService;

@Service
public class PricingRuleServiceImpl implements PricingRuleService {

    private final PricingRuleRepository repository;

    public PricingRuleServiceImpl(PricingRuleRepository repository) {
        this.repository = repository;
    }

    @Override
    public PricingRule createRule(PricingRule rule) {
        if (rule.getPriceMultiplier() <= 0) {
            throw new IllegalArgumentException("Invalid multiplier");
        }
        return repository.save(rule);
    }

    @Override
    public PricingRule updateRule(Long id, PricingRule updatedRule) {
        updatedRule.setId(id);
        return repository.save(updatedRule);
    }

    @Override
    public List<PricingRule> getActiveRules() {
        return repository.findByActiveTrue();
    }

    @Override
    public PricingRule getRuleByCode(String ruleCode) {
        return repository.findByRuleCode(ruleCode);
    }

    @Override
    public List<PricingRule> getAllRules() {
        return repository.findAll();
    }
}
