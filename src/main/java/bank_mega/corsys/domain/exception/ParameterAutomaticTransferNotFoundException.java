package bank_mega.corsys.domain.exception;

import bank_mega.corsys.domain.model.parameter.automatictransfer.ParameterAutomaticTransferId;

public class ParameterAutomaticTransferNotFoundException extends DomainException {

    public ParameterAutomaticTransferNotFoundException(ParameterAutomaticTransferId id) {
        super("ParameterAutomaticTransfer not found with id: " + id.value());
    }

}
