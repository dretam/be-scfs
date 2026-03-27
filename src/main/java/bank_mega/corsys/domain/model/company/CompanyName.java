package bank_mega.corsys.domain.model.company;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;

public record CompanyName(String value) {
    public CompanyName {
        if (value == null || value.isBlank()) {
            throw new DomainRuleViolationException("CompanyName cannot be null or blank");
        }
    }
}
