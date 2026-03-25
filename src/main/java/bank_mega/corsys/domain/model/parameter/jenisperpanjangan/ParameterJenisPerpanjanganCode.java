package bank_mega.corsys.domain.model.parameter.jenisperpanjangan;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;

public record ParameterJenisPerpanjanganCode(String value) {

    public ParameterJenisPerpanjanganCode {
        if (value == null || value.isBlank()) {
            throw new DomainRuleViolationException("ParameterJenisPerpanjanganCode cannot be null or blank");
        }
        if (value.length() != 1) {
            throw new DomainRuleViolationException("ParameterJenisPerpanjanganCode must be exactly 1 character");
        }
    }

}
