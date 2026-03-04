package bank_mega.corsys.domain.model.menu;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;

public record MenuCode(String value) {

    public MenuCode {
        if (value == null || value.isBlank()) {
            throw new DomainRuleViolationException("MenuCode cannot be null or blank");
        }
        if (!value.matches("^[A-Z_]+$")) {
            throw new DomainRuleViolationException(
                    "MenuCode must be uppercase with underscores (e.g., MENU_USER_MANAGEMENT)"
            );
        }
    }

}
