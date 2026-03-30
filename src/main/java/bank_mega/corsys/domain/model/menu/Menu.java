package bank_mega.corsys.domain.model.menu;

import bank_mega.corsys.domain.model.common.AuditTrail;
import bank_mega.corsys.domain.model.permission.Permission;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class Menu {

    private final MenuId id;
    private MenuName name;
    private MenuCode code;
    private String path;
    private String icon;
    private MenuId parentId;
    private Integer sortOrder;
    private Set<Permission> permissions;
    private AuditTrail audit;
    private List<Menu> children;

    public Menu(MenuId id,
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
        this.permissions = new HashSet<>();
        this.children = new ArrayList<>();
    }

    public void changeName(MenuName newName) {
        if (newName != null) {
            this.name = newName;
        }
    }

    public void addPermission(Permission permission) {
        if (permission != null) {
            this.permissions.add(permission);
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

    public void updateAudit(String updatedBy) {
        this.audit = this.audit.update(updatedBy);
    }

    public void deleteAudit(String deletedBy) {
        this.audit = this.audit.delete(deletedBy);
    }

}
