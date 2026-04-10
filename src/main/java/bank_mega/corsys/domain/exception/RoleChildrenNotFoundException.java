package bank_mega.corsys.domain.exception;

import bank_mega.corsys.domain.model.role.RoleCode;
import bank_mega.corsys.domain.model.role.RoleName;
import bank_mega.corsys.domain.model.rolechildren.RoleChildrenCode;
import bank_mega.corsys.domain.model.rolechildren.RoleChildrenName;

public class RoleChildrenNotFoundException extends DomainException {

    public RoleChildrenNotFoundException(RoleChildrenCode roleChildrenId) {
        super("Role children not found with id: " + roleChildrenId.value());
    }

    public RoleChildrenNotFoundException(RoleChildrenName roleChildrenName) {
        super("Role children not found with name: " + roleChildrenName.value());
    }

}
