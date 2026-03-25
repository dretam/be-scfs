package bank_mega.corsys.domain.model.parameter.jenisperpanjangan;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;

public record ParameterJenisPerpanjanganValue(String value) {

    public ParameterJenisPerpanjanganValue {
        if (value == null || value.isBlank()) {
            throw new DomainRuleViolationException("ParameterJenisPerpanjanganValue cannot be null or blank");
        }
    }

}
