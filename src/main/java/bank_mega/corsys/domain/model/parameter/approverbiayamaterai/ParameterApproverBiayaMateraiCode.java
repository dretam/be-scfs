package bank_mega.corsys.domain.model.parameter.approverbiayamaterai;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;

public record ParameterApproverBiayaMateraiCode(String value) {

    public ParameterApproverBiayaMateraiCode {
        if (value == null || value.isBlank()) {
            throw new DomainRuleViolationException("ParameterApproverBiayaMateraiCode cannot be null or blank");
        }
        if (value.length() != 1) {
            throw new DomainRuleViolationException("ParameterApproverBiayaMateraiCode must be exactly 1 character");
        }
    }

}
