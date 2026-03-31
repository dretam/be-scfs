package bank_mega.corsys.domain.model.company;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;

import java.util.UUID;

public record CompanyId(UUID value) {

    public CompanyId {
        if (value == null) {
            throw new DomainRuleViolationException("CompanyId cannot be null or be blank");
        }
    }

    public static CompanyId of(UUID value) {
        return new CompanyId(value);
    }

}
