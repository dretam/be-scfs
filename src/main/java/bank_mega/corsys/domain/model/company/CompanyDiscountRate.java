package bank_mega.corsys.domain.model.company;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;

public record CompanyDiscountRate(Integer value) {
    public CompanyDiscountRate {
        if (value == null || value < 0) {
            throw new DomainRuleViolationException("CompanyDiscountRate cannot be null or negative");
        }
    }

    public static CompanyDiscountRate of(Integer value) {
        return new CompanyDiscountRate(value);
    }
}
