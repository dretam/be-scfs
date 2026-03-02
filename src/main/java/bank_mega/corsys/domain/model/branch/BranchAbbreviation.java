package bank_mega.corsys.domain.model.branch;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;

public record BranchAbbreviation(String value) {
    public BranchAbbreviation {
        if (value == null || value.isBlank()) {
            throw new DomainRuleViolationException("BranchAbbreviation value cannot be null or blank");
        }
    }

    public static BranchAbbreviation of(String value) {
        return new BranchAbbreviation(value);
    }
}
