package bank_mega.corsys.domain.model.rolechildren;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;

public record RoleChildrenName(String value) {

    public RoleChildrenName {
        if (value == null || value.isBlank()) {
            throw new DomainRuleViolationException("RoleChildrenName cannot be null or blank");
        }
    }

}
