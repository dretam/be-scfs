package bank_mega.corsys.domain.model.parameter.jenisnasabahpenerima;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;

public record ParameterJenisNasabahPenerimaId(Integer value) {

    public ParameterJenisNasabahPenerimaId {
        if (value == null || value <= 0) {
            throw new DomainRuleViolationException("ParameterJenisNasabahPenerimaId must be a positive number");
        }
    }

    public static ParameterJenisNasabahPenerimaId of(Integer value) {
        return new ParameterJenisNasabahPenerimaId(value);
    }

}
