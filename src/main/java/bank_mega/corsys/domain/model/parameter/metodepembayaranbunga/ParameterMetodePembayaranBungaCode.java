package bank_mega.corsys.domain.model.parameter.metodepembayaranbunga;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;

public record ParameterMetodePembayaranBungaCode(String value) {

    public ParameterMetodePembayaranBungaCode {
        if (value == null || value.isBlank()) {
            throw new DomainRuleViolationException("ParameterMetodePembayaranBungaCode cannot be null or blank");
        }
        if (value.length() != 1) {
            throw new DomainRuleViolationException("ParameterMetodePembayaranBungaCode must be exactly 1 character");
        }
    }

}
