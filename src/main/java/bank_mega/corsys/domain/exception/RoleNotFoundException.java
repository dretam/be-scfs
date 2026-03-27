package bank_mega.corsys.domain.exception;

import bank_mega.corsys.domain.model.role.RoleCode;
import bank_mega.corsys.domain.model.role.RoleName;

public class RoleNotFoundException extends DomainException {

    public RoleNotFoundException(RoleCode roleId) {
        super("Role not found with id: " + roleId.value());
    }

    public RoleNotFoundException(RoleName roleName) {
        super("Role not found with name: " + roleName.value());
    }

}
