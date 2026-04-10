package bank_mega.corsys.domain.model.rolechildren;

import bank_mega.corsys.domain.model.common.AuditTrail;
import bank_mega.corsys.domain.model.menu.Menu;
import bank_mega.corsys.domain.model.permission.Permission;
import bank_mega.corsys.domain.model.role.Role;
import bank_mega.corsys.domain.model.role.RoleCode;
import bank_mega.corsys.domain.model.role.RoleIcon;
import bank_mega.corsys.domain.model.role.RoleName;
import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
public class RoleChildren {

    private RoleChildrenName name;
    private RoleChildrenCode code;
    private RoleChildrenIcon icon;
    private Role role;
    private String description;
    private Set<Permission> permissions;
    private Set<Menu> menus;
    private AuditTrail audit;

    public RoleChildren(
            RoleChildrenName name,
            RoleChildrenCode code,
            RoleChildrenIcon icon,
            Role role,
            String description,
            AuditTrail audit
    ) {
        this.name = name;
        this.code = code;
        this.icon = icon;
        this.description = description;
        this.audit = audit;
        this.role = role;
        this.permissions = new HashSet<>();
        this.menus = new HashSet<>();
    }

    public Set<Permission> getPermissions() {
        return Collections.unmodifiableSet(permissions);
    }

    public Set<Menu> getMenus() {
        return Collections.unmodifiableSet(menus);
    }


    public void changeName(RoleChildrenName newName) {
        if (newName != null) {
            this.name = newName;
        }
    }

    public void changeIcon(RoleChildrenIcon newIcon) {
        if (newIcon != null) {
            this.icon = newIcon;
        }
    }

    public void changeDescription(String description) {
        if (description != null) {
            this.description = description;
        }
    }

    public void addRole(Role role) {
        if (role != null) {
            this.role = role;
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

    public void updateAudit(UUID updatedBy) {
        this.audit = this.audit.update(updatedBy);
    }

    public void deleteAudit(UUID deletedBy) {
        this.audit = this.audit.delete(deletedBy);
    }

}
