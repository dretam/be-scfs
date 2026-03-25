package bank_mega.corsys.domain.model.parameter.jenistransfer;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;

public record ParameterJenisTransferId(Integer value) {

    public ParameterJenisTransferId {
        if (value == null || value <= 0) {
            throw new DomainRuleViolationException("ParameterJenisTransferId must be a positive number");
        }
    }

    public static ParameterJenisTransferId of(Integer value) {
        return new ParameterJenisTransferId(value);
    }

}
