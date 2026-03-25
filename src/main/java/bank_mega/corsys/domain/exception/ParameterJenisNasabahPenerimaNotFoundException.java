package bank_mega.corsys.domain.exception;

import bank_mega.corsys.domain.model.parameter.jenisnasabahpenerima.ParameterJenisNasabahPenerimaId;

public class ParameterJenisNasabahPenerimaNotFoundException extends DomainException {

    public ParameterJenisNasabahPenerimaNotFoundException(ParameterJenisNasabahPenerimaId id) {
        super("ParameterJenisNasabahPenerima not found with id: " + id.value());
    }

}
