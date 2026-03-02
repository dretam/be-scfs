package bank_mega.corsys.domain.model.branch;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;

public record BranchId(Long value) {
    public BranchId {
        if (value == null) {
            throw new DomainRuleViolationException("BranchId value cannot be null");
        }
    }

    public static BranchId of(Long value) {
        return new BranchId(value);
    }
}
