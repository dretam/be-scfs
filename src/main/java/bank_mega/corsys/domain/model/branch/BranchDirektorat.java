package bank_mega.corsys.domain.model.branch;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;

public record BranchDirektorat(String value) {
    public BranchDirektorat {
        if (value == null || value.isBlank()) {
            throw new DomainRuleViolationException("BranchDirektorat value cannot be null or blank");
        }
    }

    public static BranchDirektorat of(String value) {
        return new BranchDirektorat(value);
    }
}
