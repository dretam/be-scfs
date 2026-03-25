package bank_mega.corsys.domain.model.parameter.metodepembayaranbunga;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;

public record ParameterMetodePembayaranBungaId(String value) {

    public ParameterMetodePembayaranBungaId {
        if (value == null || value.isBlank()) {
            throw new DomainRuleViolationException("ParameterMetodePembayaranBungaId cannot be null or blank");
        }
    }

    public static ParameterMetodePembayaranBungaId of(String value) {
        return new ParameterMetodePembayaranBungaId(value);
    }

}
