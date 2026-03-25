package bank_mega.corsys.domain.model.parameter.automatictransfer;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;

public record ParameterAutomaticTransferId(String value) {

    public ParameterAutomaticTransferId {
        if (value == null || value.isBlank()) {
            throw new DomainRuleViolationException("ParameterAutomaticTransferId cannot be null or blank");
        }
    }

    public static ParameterAutomaticTransferId of(String value) {
        return new ParameterAutomaticTransferId(value);
    }

}
