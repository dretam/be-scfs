package bank_mega.corsys.domain.model.parameter.sumberdana;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;

public record ParameterSumberDanaValue(String value) {

    public ParameterSumberDanaValue {
        if (value == null || value.isBlank()) {
            throw new DomainRuleViolationException("ParameterSumberDanaValue cannot be null or blank");
        }
    }

}
