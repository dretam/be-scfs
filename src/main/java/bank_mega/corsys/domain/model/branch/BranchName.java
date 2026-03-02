package bank_mega.corsys.domain.model.branch;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;

public record BranchName(String value) {
    public BranchName {
        if (value == null || value.isBlank()) {
            throw new DomainRuleViolationException("BranchName value cannot be null or blank");
        }
    }

    public static BranchName of(String value) {
        return new BranchName(value);
    }
}
