package bank_mega.corsys.domain.model.parameter.sumberdana;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;

public record ParameterSumberDanaId(String value) {

    public ParameterSumberDanaId {
        if (value == null || value.isBlank()) {
            throw new DomainRuleViolationException("ParameterSumberDanaId cannot be null or blank");
        }
    }

    public static ParameterSumberDanaId of(String value) {
        return new ParameterSumberDanaId(value);
    }

}
