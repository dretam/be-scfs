package bank_mega.corsys.domain.model.parameter.jenistransaksiskn;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;

public record ParameterJenisTransaksiSknValue(String value) {

    public ParameterJenisTransaksiSknValue {
        if (value == null || value.isBlank()) {
            throw new DomainRuleViolationException("ParameterJenisTransaksiSknValue cannot be null or blank");
        }
    }

}
