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

    @Override
    public PricingRule create(PricingRule rule) {
        return repository.save(rule);
    }

    @Override
    public PricingRule update(Long id, PricingRule rule) {
        rule.setId(id);
        return repository.save(rule);
    }

    @Override
    public PricingRule getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<PricingRule> getActiveRules() {
        return repository.findByActiveTrue();
    }

    @Override
    public List<PricingRule> getAll() {
        return repository.findAll();
    }
}
