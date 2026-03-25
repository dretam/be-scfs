package bank_mega.corsys.domain.exception;

import bank_mega.corsys.domain.model.parameter.jenistransfer.ParameterJenisTransferId;

public class ParameterJenisTransferNotFoundException extends DomainException {

    public ParameterJenisTransferNotFoundException(ParameterJenisTransferId id) {
        super("ParameterJenisTransfer not found with id: " + id.value());
    }

}
