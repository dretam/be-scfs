package bank_mega.corsys.domain.model.internaluser;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;

public record InternalUserJabatan(Integer value) {
    public InternalUserJabatan {
        if (value == null) {
            throw new DomainRuleViolationException("InternalUserJabatan value cannot be null");
        }
    }

    public static InternalUserJabatan of(Integer value) {
        return new InternalUserJabatan(value);
    }
}
