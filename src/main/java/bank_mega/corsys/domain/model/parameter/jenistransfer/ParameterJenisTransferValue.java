package bank_mega.corsys.domain.model.parameter.jenistransfer;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;

public record ParameterJenisTransferValue(String value) {

    public ParameterJenisTransferValue {
        if (value == null || value.isBlank()) {
            throw new DomainRuleViolationException("ParameterJenisTransferValue cannot be null or blank");
        }
    }

}
