package bank_mega.corsys.domain.exception;

import bank_mega.corsys.domain.model.parameter.jenistransaksiskn.ParameterJenisTransaksiSknId;

public class ParameterJenisTransaksiSknNotFoundException extends DomainException {

    public ParameterJenisTransaksiSknNotFoundException(ParameterJenisTransaksiSknId id) {
        super("ParameterJenisTransaksiSkn not found with id: " + id.value());
    }

}
