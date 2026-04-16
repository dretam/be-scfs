package bank_mega.corsys.domain.model.community;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;

public record PricingTierQuantity(Integer value) {
    public PricingTierQuantity {
        if (value == null || value < 0) {
            throw new DomainRuleViolationException("PricingTierQuantity cannot be null or negative");
        }
    }
}
