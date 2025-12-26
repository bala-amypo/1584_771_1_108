package com.example.demo.service.impl;

import com.example.demo.model.PricingRule;
import com.example.demo.repository.PricingRuleRepository;
import com.example.demo.service.PricingRuleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PricingRuleServiceImpl implements PricingRuleService {

    private final PricingRuleRepository repository;

    public PricingRuleServiceImpl(PricingRuleRepository repository) {
        this.repository = repository;
    }

    @Override
    public PricingRule createRule(PricingRule rule) {
        return repository.save(rule);
    }

    @Override
    public PricingRule updateRule(Long id, PricingRule rule) {
        rule.setId(id);
        return repository.save(rule);
    }

    @Override
    public Optional<PricingRule> getRuleById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<PricingRule> getAllRules() {
        return repository.findAll();
    }

    @Override
    public List<PricingRule> getActiveRules() {
        return repository.findByActiveTrue();
    }
}
