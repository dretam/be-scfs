package bank_mega.corsys.domain.model.branch;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;

public record BranchCategory(String value) {
    public BranchCategory {
        if (value == null || value.isBlank()) {
            throw new DomainRuleViolationException("BranchCategory value cannot be null or blank");
        }
    }

    public static BranchCategory of(String value) {
        return new BranchCategory(value);
    }
}
