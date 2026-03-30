package bank_mega.corsys.domain.model.company;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;

public record CompanyMaxFinancing(Integer value) {
    public CompanyMaxFinancing {
        if (value == null || value < 0) {
            throw new DomainRuleViolationException("CompanyMaxFinancing cannot be null or negative");
        }
    }

    public static CompanyMaxFinancing of(Integer value) {
        return new CompanyMaxFinancing(value);
    }
}
