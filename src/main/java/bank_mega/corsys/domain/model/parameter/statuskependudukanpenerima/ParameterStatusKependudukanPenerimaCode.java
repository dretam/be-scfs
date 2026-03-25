package bank_mega.corsys.domain.model.parameter.statuskependudukanpenerima;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;

public record ParameterStatusKependudukanPenerimaCode(Integer value) {

    public ParameterStatusKependudukanPenerimaCode {
        if (value == null || value <= 0) {
            throw new DomainRuleViolationException("ParameterStatusKependudukanPenerimaCode must be a positive number");
        }
    }

}
