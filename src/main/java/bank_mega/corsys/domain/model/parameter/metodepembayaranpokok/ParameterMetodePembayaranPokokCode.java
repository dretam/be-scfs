package bank_mega.corsys.domain.model.parameter.metodepembayaranpokok;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;

public record ParameterMetodePembayaranPokokCode(String value) {

    public ParameterMetodePembayaranPokokCode {
        if (value == null || value.isBlank()) {
            throw new DomainRuleViolationException("ParameterMetodePembayaranPokokCode cannot be null or blank");
        }
        if (value.length() != 1) {
            throw new DomainRuleViolationException("ParameterMetodePembayaranPokokCode must be exactly 1 character");
        }
    }

}
