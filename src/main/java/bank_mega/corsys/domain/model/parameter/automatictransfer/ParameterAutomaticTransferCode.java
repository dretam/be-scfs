package bank_mega.corsys.domain.model.parameter.automatictransfer;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;

public record ParameterAutomaticTransferCode(String value) {

    public ParameterAutomaticTransferCode {
        if (value == null || value.isBlank()) {
            throw new DomainRuleViolationException("ParameterAutomaticTransferCode cannot be null or blank");
        }
        if (value.length() != 1) {
            throw new DomainRuleViolationException("ParameterAutomaticTransferCode must be exactly 1 character");
        }
    }

}
