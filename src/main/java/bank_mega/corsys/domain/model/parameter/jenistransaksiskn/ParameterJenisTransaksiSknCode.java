package bank_mega.corsys.domain.model.parameter.jenistransaksiskn;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;

public record ParameterJenisTransaksiSknCode(Integer value) {

    public ParameterJenisTransaksiSknCode {
        if (value == null || value <= 0) {
            throw new DomainRuleViolationException("ParameterJenisTransaksiSknCode must be a positive number");
        }
    }

}
