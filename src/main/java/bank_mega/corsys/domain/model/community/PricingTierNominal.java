package bank_mega.corsys.domain.model.community;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;

import java.math.BigDecimal;

public record PricingTierNominal(BigDecimal value) {
    public PricingTierNominal {
        if (value == null || value.compareTo(BigDecimal.ZERO) < 0) {
            throw new DomainRuleViolationException("PricingTierNominal cannot be null or negative");
        }
    }
}
