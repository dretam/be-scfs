package bank_mega.corsys.domain.model.role;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;

public record RoleId(Long value) {

    public RoleId {
        if (value == null || value <= 0) {
            throw new DomainRuleViolationException("RoleId cannot be null");
        }
    }

    public static RoleId of(Long value) {
        return new RoleId(value);
    }

}
