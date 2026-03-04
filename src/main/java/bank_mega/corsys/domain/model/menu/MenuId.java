package bank_mega.corsys.domain.model.menu;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;

public record MenuId(Long value) {

    public MenuId {
        if (value == null || value <= 0) {
            throw new DomainRuleViolationException("MenuId must be a positive number");
        }
    }

    public static MenuId of(Long value) {
        return new MenuId(value);
    }

}
