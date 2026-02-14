package bank_mega.corsys.domain.model.role;

import bank_mega.corsys.domain.model.common.AuditTrail;

public class Role {

    private final RoleId id;
    private RoleName name;
    private RoleIcon icon;
    private String description;
    private AuditTrail audit;

    public Role(
            RoleId id,
            RoleName name,
            RoleIcon icon,
            String description,
            AuditTrail audit
    ) {
        this.id = id;
        this.name = name;
        this.icon = icon;
        this.description = description;
        this.audit = audit;
    }

    public RoleId getId() {
        return id;
    }

    public RoleName getName() {
        return name;
    }

    public RoleIcon getIcon() {
        return icon;
    }

    public String getDescription() {
        return description;
    }

    public AuditTrail getAudit() {
        return this.audit;
    }

    public void changeName(RoleName newName) {
        if (newName != null) {
            this.name = newName;
        }
    }

    public void changeIcon(RoleIcon newIcon) {
        if (newIcon != null) {
            this.icon = newIcon;
        }
    }

    public void changeDescription(String description) {
        if (description != null) {
            this.description = description;
        }
    }

    public void updateAudit(Long updatedBy) {
        this.audit = this.audit.update(updatedBy);
    }

    public void deleteAudit(Long deletedBy) {
        this.audit = this.audit.delete(deletedBy);
    }

}
