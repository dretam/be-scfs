package bank_mega.corsys.domain.model.community;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;

import java.util.UUID;

public record PricingTierId(UUID value) {
    public PricingTierId {
        if (value == null) {
            throw new DomainRuleViolationException("PricingTierId cannot be null");
        }
    }

    public static PricingTierId of(UUID value) {
        return new PricingTierId(value);
    }
}
