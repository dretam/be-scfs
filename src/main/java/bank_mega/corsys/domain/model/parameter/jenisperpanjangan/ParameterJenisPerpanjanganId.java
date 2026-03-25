package bank_mega.corsys.domain.model.parameter.jenisperpanjangan;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;

public record ParameterJenisPerpanjanganId(String value) {

    public ParameterJenisPerpanjanganId {
        if (value == null || value.isBlank()) {
            throw new DomainRuleViolationException("ParameterJenisPerpanjanganId cannot be null or blank");
        }
    }

    public static ParameterJenisPerpanjanganId of(String value) {
        return new ParameterJenisPerpanjanganId(value);
    }

}
