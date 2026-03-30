package bank_mega.corsys.domain.model.user;

import bank_mega.corsys.domain.model.common.AuditTrail;
import bank_mega.corsys.domain.model.role.Role;
import bank_mega.corsys.domain.model.userpermission.UserPermission;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class User {

    private final UserId id;
    private UserName name;
    private UserEmail email;
    private UserPassword password;
    private Role role;
    private UserType type;
    private AuditTrail audit;
    private Set<UserPermission> userPermissionOverride;

    public User(
            UserId id,
            UserName name,
            UserEmail email,
            UserPassword password,
            Role role,
            AuditTrail audit
    ) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.type = type;
        this.audit = audit;
        this.userPermissionOverride = new HashSet<>();
    }

    public void addPermissionOverride(UserPermission permission) {
        if (permission != null) {
            this.userPermissionOverride.add(permission);
        }
    }

    public void changeName(UserName newName) {
        if (newName != null) {
            this.name = newName;
        }
    }

    public void changeEmail(UserEmail newEmail) {
        if (newEmail != null) {
            this.email = newEmail;
        }
    }

    public void changePassword(UserPassword newPassword) {
        if (newPassword != null) {
            this.password = newPassword;
        }
    }

    public void changeRole(Role newRole) {
        if (newRole != null) {
            this.role = newRole;
        }
    }

    public void updateAudit(String updatedBy) {
        this.audit = this.audit.update(updatedBy);
    }

    public void deleteAudit(String deletedBy) {
        this.audit = this.audit.delete(deletedBy);
    }

}
