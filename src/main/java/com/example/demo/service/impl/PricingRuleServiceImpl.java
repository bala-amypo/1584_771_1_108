// src/main/java/com/example/demo/service/impl/PricingRuleServiceImpl.java
package com.example.demo.service.impl;

import com.example.demo.exception.BadRequestException;
import com.example.demo.model.PricingRule;
import com.example.demo.repository.PricingRuleRepository;
import com.example.demo.service.PricingRuleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PricingRuleServiceImpl implements PricingRuleService {
    private final PricingRuleRepository ruleRepository;

    public PricingRuleServiceImpl(PricingRuleRepository ruleRepository) {
        this.ruleRepository = ruleRepository;
    }

    @Override
    public PricingRule createRule(PricingRule rule) {
        if (ruleRepository.existsByRuleCode(rule.getRuleCode()))
            throw new BadRequestException("Rule code already exists");
        if (rule.getPriceMultiplier() == null || rule.getPriceMultiplier() <= 0.0)
            throw new BadRequestException("Price multiplier must be > 0");
        return ruleRepository.save(rule);
    }

    @Override
    public PricingRule updateRule(Long id, PricingRule updatedRule) {
        Optional<PricingRule> opt = ruleRepository.findById(id);
        if (opt.isEmpty()) return null;
        PricingRule r = opt.get();
        r.setDescription(updatedRule.getDescription());
        r.setMinRemainingSeats(updatedRule.getMinRemainingSeats());
        r.setMaxRemainingSeats(updatedRule.getMaxRemainingSeats());
        r.setDaysBeforeEvent(updatedRule.getDaysBeforeEvent());
        r.setPriceMultiplier(updatedRule.getPriceMultiplier());
        r.setActive(updatedRule.getActive());
        return ruleRepository.save(r);
    }

    @Override
    public List<PricingRule> getActiveRules() {
        return ruleRepository.findByActiveTrue();
    }

    @Override
    public Optional<PricingRule> getRuleByCode(String ruleCode) {
        return ruleRepository.findAll().stream().filter(r -> ruleCode.equals(r.getRuleCode())).findFirst();
    }

    @Override
    public List<PricingRule> getAllRules() {
        return ruleRepository.findAll();
    }
}