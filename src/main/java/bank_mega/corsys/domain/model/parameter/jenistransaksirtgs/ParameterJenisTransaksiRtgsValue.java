package bank_mega.corsys.domain.model.parameter.jenistransaksirtgs;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;

public record ParameterJenisTransaksiRtgsValue(String value) {

    public ParameterJenisTransaksiRtgsValue {
        if (value == null || value.isBlank()) {
            throw new DomainRuleViolationException("ParameterJenisTransaksiRtgsValue cannot be null or blank");
        }
    }

}
