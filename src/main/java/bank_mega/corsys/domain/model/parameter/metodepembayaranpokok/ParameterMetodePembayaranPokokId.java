package bank_mega.corsys.domain.model.parameter.metodepembayaranpokok;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;

public record ParameterMetodePembayaranPokokId(String value) {

    public ParameterMetodePembayaranPokokId {
        if (value == null || value.isBlank()) {
            throw new DomainRuleViolationException("ParameterMetodePembayaranPokokId cannot be null or blank");
        }
    }

    public static ParameterMetodePembayaranPokokId of(String value) {
        return new ParameterMetodePembayaranPokokId(value);
    }

}
