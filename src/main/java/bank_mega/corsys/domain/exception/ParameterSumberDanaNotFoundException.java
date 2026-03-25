package bank_mega.corsys.domain.exception;

import bank_mega.corsys.domain.model.parameter.sumberdana.ParameterSumberDanaId;

public class ParameterSumberDanaNotFoundException extends DomainException {

    public ParameterSumberDanaNotFoundException(ParameterSumberDanaId id) {
        super("ParameterSumberDana not found with id: " + id.value());
    }

}
