package bank_mega.corsys.domain.model.parameter.sumberdana;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;

public record ParameterSumberDanaCode(String value) {

    public ParameterSumberDanaCode {
        if (value == null || value.isBlank()) {
            throw new DomainRuleViolationException("ParameterSumberDanaCode cannot be null or blank");
        }
        if (value.length() != 1) {
            throw new DomainRuleViolationException("ParameterSumberDanaCode must be exactly 1 character");
        }
    }

}
