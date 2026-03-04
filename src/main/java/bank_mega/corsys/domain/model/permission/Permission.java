package bank_mega.corsys.domain.model.permission;

import bank_mega.corsys.domain.model.common.AuditTrail;

public class Permission {

    private final PermissionId id;
    private PermissionName name;
    private PermissionCode code;
    private String description;
    private AuditTrail audit;

    public Permission(
            PermissionId id,
            PermissionName name,
            PermissionCode code,
            String description,
            AuditTrail audit
    ) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.description = description;
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

    public void updateAudit(Long updatedBy) {
        this.audit = this.audit.update(updatedBy);
    }

    public void deleteAudit(Long deletedBy) {
        this.audit = this.audit.delete(deletedBy);
    }

}
