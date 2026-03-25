package bank_mega.corsys.domain.model.parameter.jenistransaksirtgs;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;

public record ParameterJenisTransaksiRtgsId(Integer value) {

    public ParameterJenisTransaksiRtgsId {
        if (value == null || value <= 0) {
            throw new DomainRuleViolationException("ParameterJenisTransaksiRtgsId must be a positive number");
        }
    }

    public static ParameterJenisTransaksiRtgsId of(Integer value) {
        return new ParameterJenisTransaksiRtgsId(value);
    }

}
