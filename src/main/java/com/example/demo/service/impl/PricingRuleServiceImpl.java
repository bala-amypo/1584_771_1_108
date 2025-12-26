package com.example.demo.service.impl;

import com.example.demo.model.PricingRule;
import com.example.demo.repository.PricingRuleRepository;
import com.example.demo.service.PricingRuleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PricingRuleServiceImpl implements PricingRuleService {

    private final PricingRuleRepository repo;

    public PricingRuleServiceImpl(PricingRuleRepository repo) {
        this.repo = repo;
    }

    @Override
    public PricingRule createRule(PricingRule rule) {
        return repo.save(rule);
    }

    @Override
    public PricingRule updateRule(long id, PricingRule rule) {
        rule.setId(id);
        return repo.save(rule);
    }

    @Override
    public List<PricingRule> getAllRules() {
        return repo.findAll();
    }

    @Override
    public List<PricingRule> getActiveRules() {
        return repo.findByActiveTrue();
    }
}
