package bank_mega.corsys.domain.exception;

import bank_mega.corsys.domain.model.parameter.approverbiayamaterai.ParameterApproverBiayaMateraiId;

public class ParameterApproverBiayaMateraiNotFoundException extends DomainException {

    public ParameterApproverBiayaMateraiNotFoundException(ParameterApproverBiayaMateraiId id) {
        super("ParameterApproverBiayaMaterai not found with id: " + id.value());
    }

}
