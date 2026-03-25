package bank_mega.corsys.domain.model.parameter.approverbiayamaterai;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;

public record ParameterApproverBiayaMateraiId(String value) {

    public ParameterApproverBiayaMateraiId {
        if (value == null || value.isBlank()) {
            throw new DomainRuleViolationException("ParameterApproverBiayaMateraiId cannot be null or blank");
        }
    }

    public static ParameterApproverBiayaMateraiId of(String value) {
        return new ParameterApproverBiayaMateraiId(value);
    }

}
