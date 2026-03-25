package bank_mega.corsys.domain.model.parameter.jenistransfer;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;

public record ParameterJenisTransferCode(Integer value) {

    public ParameterJenisTransferCode {
        if (value == null || value <= 0) {
            throw new DomainRuleViolationException("ParameterJenisTransferCode must be a positive number");
        }
    }

}
