package bank_mega.corsys.domain.exception;

import bank_mega.corsys.domain.model.parameter.statuskependudukanpenerima.ParameterStatusKependudukanPenerimaId;

public class ParameterStatusKependudukanPenerimaNotFoundException extends DomainException {

    public ParameterStatusKependudukanPenerimaNotFoundException(ParameterStatusKependudukanPenerimaId id) {
        super("ParameterStatusKependudukanPenerima not found with id: " + id.value());
    }

}
