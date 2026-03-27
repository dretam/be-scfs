package bank_mega.corsys.domain.model.company;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;

public record CompanyCif(String value) {
    public CompanyCif {
        if (value == null || value.isBlank()) {
            throw new DomainRuleViolationException("CompanyCif cannot be null or blank");
        }
    }
}
