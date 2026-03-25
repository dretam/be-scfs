package bank_mega.corsys.domain.exception;

import bank_mega.corsys.domain.model.parameter.metodepembayaranpokok.ParameterMetodePembayaranPokokId;

public class ParameterMetodePembayaranPokokNotFoundException extends DomainException {

    public ParameterMetodePembayaranPokokNotFoundException(ParameterMetodePembayaranPokokId id) {
        super("ParameterMetodePembayaranPokok not found with id: " + id.value());
    }

}
