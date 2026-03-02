package bank_mega.corsys.domain.model.internaluser;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;

public record InternalUserStatus(Integer value) {
    public InternalUserStatus {
        if (value == null) {
            throw new DomainRuleViolationException("InternalUserStatus value cannot be null");
        }
    }

    public static InternalUserStatus of(Integer value) {
        return new InternalUserStatus(value);
    }
}
