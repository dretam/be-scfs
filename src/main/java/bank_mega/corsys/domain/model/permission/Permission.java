package bank_mega.corsys.domain.model.permission;

import bank_mega.corsys.domain.model.common.AuditTrail;
import bank_mega.corsys.domain.model.menu.Menu;
import bank_mega.corsys.domain.model.menu.MenuId;

public class Permission {

    private final PermissionId id;
    private PermissionName name;
    private PermissionCode code;
    private String description;
    private MenuId menuId;
    private AuditTrail audit;

    public Permission(
            PermissionId id,
            PermissionName name,
            PermissionCode code,
            String description,
            MenuId menuId,
            AuditTrail audit
    ) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.description = description;
        this.menuId = menuId;
        this.audit = audit;
    }

    public PermissionId getId() {
        return id;
    }

    public PermissionName getName() {
        return name;
    }

    public PermissionCode getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public MenuId getMenuId() {
        return menuId;
    }

    public AuditTrail getAudit() {
        return this.audit;
    }

    public void changeName(PermissionName newName) {
        if (newName != null) {
            this.name = newName;
        }
    }

    public void changeCode(PermissionCode newCode) {
        if (newCode != null) {
            this.code = newCode;
        }
    }

    public void changeDescription(String description) {
        if (description != null) {
            this.description = description;
        }
    }

    public void changeMenuId(MenuId menuId) {
        this.menuId = menuId;
    }

    public void updateAudit(String updatedBy) {
        this.audit = this.audit.update(updatedBy);
    }

    public void deleteAudit(String deletedBy) {
        this.audit = this.audit.delete(deletedBy);
    }

}
