package bank_mega.corsys.domain.exception;

import bank_mega.corsys.domain.model.parameter.jenisperpanjangan.ParameterJenisPerpanjanganId;

public class ParameterJenisPerpanjanganNotFoundException extends DomainException {

    public ParameterJenisPerpanjanganNotFoundException(ParameterJenisPerpanjanganId id) {
        super("ParameterJenisPerpanjangan not found with id: " + id.value());
    }

}
