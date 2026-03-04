package bank_mega.corsys.domain.model.menu;

import bank_mega.corsys.domain.model.common.AuditTrail;

public class Menu {

    private final MenuId id;
    private MenuName name;
    private MenuCode code;
    private String path;
    private String icon;
    private MenuId parentId;
    private Integer sortOrder;
    private AuditTrail audit;

    public Menu(
            MenuId id,
            MenuName name,
            MenuCode code,
            String path,
            String icon,
            MenuId parentId,
            Integer sortOrder,
            AuditTrail audit
    ) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.path = path;
        this.icon = icon;
        this.parentId = parentId;
        this.sortOrder = sortOrder;
        this.audit = audit;
    }

    public MenuId getId() {
        return id;
    }

    public MenuName getName() {
        return name;
    }

    public MenuCode getCode() {
        return code;
    }

    public String getPath() {
        return path;
    }

    public String getIcon() {
        return icon;
    }

    public MenuId getParentId() {
        return parentId;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public AuditTrail getAudit() {
        return this.audit;
    }

    public void changeName(MenuName newName) {
        if (newName != null) {
            this.name = newName;
        }
    }

    public void changeCode(MenuCode newCode) {
        if (newCode != null) {
            this.code = newCode;
        }
    }

    public void changePath(String path) {
        if (path != null) {
            this.path = path;
        }
    }

    public void changeIcon(String icon) {
        if (icon != null) {
            this.icon = icon;
        }
    }

    public void changeParent(MenuId parentId) {
        this.parentId = parentId;
    }

    public void changeSortOrder(Integer sortOrder) {
        if (sortOrder != null) {
            this.sortOrder = sortOrder;
        }
    }

    public void updateAudit(Long updatedBy) {
        this.audit = this.audit.update(updatedBy);
    }

    public void deleteAudit(Long deletedBy) {
        this.audit = this.audit.delete(deletedBy);
    }

}
