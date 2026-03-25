package bank_mega.corsys.domain.model.parameter.jenistransaksiskn;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;

public record ParameterJenisTransaksiSknId(Integer value) {

    public ParameterJenisTransaksiSknId {
        if (value == null || value <= 0) {
            throw new DomainRuleViolationException("ParameterJenisTransaksiSknId must be a positive number");
        }
    }

    public static ParameterJenisTransaksiSknId of(Integer value) {
        return new ParameterJenisTransaksiSknId(value);
    }

}
