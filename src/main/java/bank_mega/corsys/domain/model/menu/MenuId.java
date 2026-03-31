package bank_mega.corsys.domain.model.menu;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;

import java.util.UUID;

public record MenuId(UUID value) {

    public MenuId {
        if (value == null) {
            throw new DomainRuleViolationException("MenuId must be a positive number");
        }
    }

    public static MenuId of(UUID value) {
        return new MenuId(value);
    }

}
