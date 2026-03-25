package bank_mega.corsys.domain.model.parameter.automatictransfer;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;

public record ParameterAutomaticTransferValue(String value) {

    public ParameterAutomaticTransferValue {
        if (value == null || value.isBlank()) {
            throw new DomainRuleViolationException("ParameterAutomaticTransferValue cannot be null or blank");
        }
    }

}
