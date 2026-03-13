package bank_mega.corsys.domain.model.role;

import bank_mega.corsys.domain.model.common.AuditTrail;
import bank_mega.corsys.domain.model.menu.Menu;
import bank_mega.corsys.domain.model.permission.Permission;
import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class Role {

    private final RoleId id;
    private RoleName name;
    private RoleCode code;
    private RoleIcon icon;
    private String description;
    private Set<Permission> permissions;
    private Set<Menu> menus;
    private AuditTrail audit;

    public Role(
            RoleId id,
            RoleName name,
            RoleCode code,
            RoleIcon icon,
            String description,
            AuditTrail audit
    ) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.icon = icon;
        this.description = description;
        this.audit = audit;
        this.permissions = new HashSet<>();
        this.menus = new HashSet<>();
    }

    public Set<Permission> getPermissions() {
        return Collections.unmodifiableSet(permissions);
    }

    public Set<Menu> getMenus() {
        return Collections.unmodifiableSet(menus);
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

    public void addPermission(Permission permission) {
        if (permission != null) {
            this.permissions.add(permission);
        }
    }

    public void removePermission(Permission permission) {
        if (permission != null) {
            this.permissions.remove(permission);
        }
    }

    public void addMenu(Menu menu) {
        if (menu != null) {
            this.menus.add(menu);
        }
    }

    public void removeMenu(Menu menu) {
        if (menu != null) {
            this.menus.remove(menu);
        }
    }

    public void updateAudit(Long updatedBy) {
        this.audit = this.audit.update(updatedBy);
    }

    public void deleteAudit(Long deletedBy) {
        this.audit = this.audit.delete(deletedBy);
    }

}
