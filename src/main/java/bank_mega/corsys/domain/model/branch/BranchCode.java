package bank_mega.corsys.domain.model.branch;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;

public record BranchCode(String value) {
    public BranchCode {
        if (value == null || value.isBlank()) {
            throw new DomainRuleViolationException("BranchCode value cannot be null or blank");
        }
    }

    public static BranchCode of(String value) {
        return new BranchCode(value);
    }
}
