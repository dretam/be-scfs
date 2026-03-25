package bank_mega.corsys.domain.model.parameter.jenisnasabahpenerima;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;

public record ParameterJenisNasabahPenerimaValue(String value) {

    public ParameterJenisNasabahPenerimaValue {
        if (value == null || value.isBlank()) {
            throw new DomainRuleViolationException("ParameterJenisNasabahPenerimaValue cannot be null or blank");
        }
    }

}
