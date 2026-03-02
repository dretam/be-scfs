package bank_mega.corsys.domain.model.branch;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;

public record BranchArea(String value) {
    public BranchArea {
        if (value == null || value.isBlank()) {
            throw new DomainRuleViolationException("BranchArea value cannot be null or blank");
        }
    }

    public static BranchArea of(String value) {
        return new BranchArea(value);
    }
}
