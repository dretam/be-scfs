package bank_mega.corsys.domain.model.parameter.statuskependudukanpenerima;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;

public record ParameterStatusKependudukanPenerimaId(Integer value) {

    public ParameterStatusKependudukanPenerimaId {
        if (value == null || value <= 0) {
            throw new DomainRuleViolationException("ParameterStatusKependudukanPenerimaId must be a positive number");
        }
    }

    public static ParameterStatusKependudukanPenerimaId of(Integer value) {
        return new ParameterStatusKependudukanPenerimaId(value);
    }

}
