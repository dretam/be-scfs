package bank_mega.corsys.domain.model.company;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;

public record CompanyId(String value) {

    public CompanyId {
        if (value == null || value.isEmpty()) {
            throw new DomainRuleViolationException("CompanyId cannot be null or be blank");
        }
    }

    public static CompanyId of(String value) {
        return new CompanyId(value);
    }

}
