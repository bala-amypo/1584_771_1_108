package com.example.demo.service.impl;

import com.example.demo.model.PricingRule;
import com.example.demo.repository.PricingRuleRepository;
import com.example.demo.service.PricingRuleService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PricingRuleServiceImpl implements PricingRuleService {

    private final PricingRuleRepository repository;

    public PricingRuleServiceImpl(PricingRuleRepository repository) {
        this.repository = repository;
    }

    public PricingRule createRule(PricingRule rule) {
        return repository.save(rule);
    }

    public PricingRule updateRule(long id, PricingRule rule) {
        rule.setId(id);
        return repository.save(rule);
    }

    public PricingRule getById(long id) {
        return repository.findById(id).orElse(null);
    }

    public List<PricingRule> getAllRules() {
        return repository.findAll();
    }
}
