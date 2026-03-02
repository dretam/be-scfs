package bank_mega.corsys.domain.model.branch;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;

public record BranchRegional(String value) {
    public BranchRegional {
        if (value == null || value.isBlank()) {
            throw new DomainRuleViolationException("BranchRegional value cannot be null or blank");
        }
    }

    public static BranchRegional of(String value) {
        return new BranchRegional(value);
    }
}
