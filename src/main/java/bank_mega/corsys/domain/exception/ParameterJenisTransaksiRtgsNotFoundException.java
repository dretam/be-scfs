package bank_mega.corsys.domain.exception;

import bank_mega.corsys.domain.model.parameter.jenistransaksirtgs.ParameterJenisTransaksiRtgsId;

public class ParameterJenisTransaksiRtgsNotFoundException extends DomainException {

    public ParameterJenisTransaksiRtgsNotFoundException(ParameterJenisTransaksiRtgsId id) {
        super("ParameterJenisTransaksiRtgs not found with id: " + id.value());
    }

}
