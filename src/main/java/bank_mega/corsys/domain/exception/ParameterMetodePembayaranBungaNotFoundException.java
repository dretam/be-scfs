package bank_mega.corsys.domain.exception;

import bank_mega.corsys.domain.model.parameter.metodepembayaranbunga.ParameterMetodePembayaranBungaId;

public class ParameterMetodePembayaranBungaNotFoundException extends DomainException {

    public ParameterMetodePembayaranBungaNotFoundException(ParameterMetodePembayaranBungaId id) {
        super("ParameterMetodePembayaranBunga not found with id: " + id.value());
    }

}
