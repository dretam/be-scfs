package bank_mega.corsys.domain.model.parameter.metodepembayaranpokok;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;

public record ParameterMetodePembayaranPokokValue(String value) {

    public ParameterMetodePembayaranPokokValue {
        if (value == null || value.isBlank()) {
            throw new DomainRuleViolationException("ParameterMetodePembayaranPokokValue cannot be null or blank");
        }
    }

}
