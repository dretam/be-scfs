package bank_mega.corsys.domain.model.menu;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;

public record MenuName(String value) {

    public MenuName {
        if (value == null || value.isBlank()) {
            throw new DomainRuleViolationException("MenuName cannot be null or blank");
        }
    }

}
