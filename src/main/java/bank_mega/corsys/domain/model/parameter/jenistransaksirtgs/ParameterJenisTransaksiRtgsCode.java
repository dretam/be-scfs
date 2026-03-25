package bank_mega.corsys.domain.model.parameter.jenistransaksirtgs;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;

public record ParameterJenisTransaksiRtgsCode(Integer value) {

    public ParameterJenisTransaksiRtgsCode {
        if (value == null || value <= 0) {
            throw new DomainRuleViolationException("ParameterJenisTransaksiRtgsCode must be a positive number");
        }
    }

}
