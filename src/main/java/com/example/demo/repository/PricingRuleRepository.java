package com.example.demo.repository;

import com.example.demo.model.PricingRule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PricingRuleRepository
        extends JpaRepository<PricingRule, Long> {

    boolean existsByRuleCode(String ruleCode);

    Optional<PricingRule> findByRuleCode(String ruleCode);

    List<PricingRule> findByActiveTrue();
}
