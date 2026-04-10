package bank_mega.corsys.domain.model.rolechildren;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;

public record RoleChildrenIcon(String value) {

    public RoleChildrenIcon {
        if (value == null || value.isBlank()) {
            throw new DomainRuleViolationException("RoleIcon RoleChildrenIcon be null or blank");
        }
    }

}
