package bank_mega.corsys.domain.model.internaluser;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;

public record InternalEmployeeType(String value) {
    public InternalEmployeeType {
        if (value == null || value.isBlank()) {
            throw new DomainRuleViolationException("InternalEmployeeType value cannot be null or blank");
        }
    }

    public static InternalEmployeeType of(String value) {
        return new InternalEmployeeType(value);
    }
}
