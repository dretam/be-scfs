package bank_mega.corsys.domain.model.parameter.metodepembayaranbunga;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;

public record ParameterMetodePembayaranBungaValue(String value) {

    public ParameterMetodePembayaranBungaValue {
        if (value == null || value.isBlank()) {
            throw new DomainRuleViolationException("ParameterMetodePembayaranBungaValue cannot be null or blank");
        }
    }

}
