package bank_mega.corsys.domain.model.internaluser;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;

public record InternalUserDirektorat(String value) {
    public InternalUserDirektorat {
        if (value == null || value.isBlank()) {
            throw new DomainRuleViolationException("InternalUserDirektorat value cannot be null or blank");
        }
    }

    public static InternalUserDirektorat of(String value) {
        return new InternalUserDirektorat(value);
    }
}
