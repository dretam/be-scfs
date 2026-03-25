package bank_mega.corsys.domain.model.parameter.statuskependudukanpenerima;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;

public record ParameterStatusKependudukanPenerimaValue(String value) {

    public ParameterStatusKependudukanPenerimaValue {
        if (value == null || value.isBlank()) {
            throw new DomainRuleViolationException("ParameterStatusKependudukanPenerimaValue cannot be null or blank");
        }
    }

}
