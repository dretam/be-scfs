package bank_mega.corsys.domain.model.parameter.approverbiayamaterai;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;

public record ParameterApproverBiayaMateraiValue(String value) {

    public ParameterApproverBiayaMateraiValue {
        if (value == null || value.isBlank()) {
            throw new DomainRuleViolationException("ParameterApproverBiayaMateraiValue cannot be null or blank");
        }
    }

}
