package bank_mega.corsys.domain.model.common;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;

public record Id(Long value) {
    public Id {
        if (value == null) {
            throw new DomainRuleViolationException("Id value cannot be null");
        }
    }

    public static Id of(Long value) {
        return new Id(value);
    }
}