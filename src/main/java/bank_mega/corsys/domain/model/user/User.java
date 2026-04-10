package bank_mega.corsys.domain.model.user;

import bank_mega.corsys.domain.model.common.AuditTrail;
import bank_mega.corsys.domain.model.company.Company;
import bank_mega.corsys.domain.model.role.Role;
import bank_mega.corsys.domain.model.rolechildren.RoleChildren;
import bank_mega.corsys.domain.model.userpermission.UserPermission;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
public class User {

    private final UserId id;
    private UserName name;
    private UserFullName fullName;
    private UserEmail email;
    private UserPassword password;
    private UserIsActive isActive;
    private UserPhotoPath photoPath;
    private Company company;
    private Role role;
    private UserType type;
    private AuditTrail audit;
    private Set<UserPermission> userPermissionOverride;
    private RoleChildren roleChildren;

    public User(
            UserId id,
            UserName name,
            UserFullName fullName,
            UserEmail email,
            UserPassword password,
            UserIsActive isActive,
            UserPhotoPath photoPath,
            Company company,
            Role role,
            RoleChildren roleChildren,
            AuditTrail audit
    ) {
        this.id = id;
        this.name = name;
        this.fullName = fullName;
        this.email = email;
        this.isActive = isActive;
        this.photoPath = photoPath;
        this.password = password;
        this.type = type;
        this.role = role;
        this.roleChildren = roleChildren;
        this.company = company;
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

    public void changeFullName(UserFullName newFullName) {
        if (newFullName != null) {
            this.fullName = newFullName;
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

    public void changeIsActive(UserIsActive isActive) {
        if (isActive != null) {
            this.isActive = isActive;
        }
    }

    public void changePhotoPath(UserPhotoPath photoPath) {
        if (photoPath != null) {
            this.photoPath = photoPath;
        }
    }

    public void changeRole(Role newRole) {
        if (newRole != null) {
            this.role = newRole;
        }
    }

    public void changeRoleChildren(RoleChildren newRoleChildren) {
        if (newRoleChildren != null) {
            this.roleChildren = newRoleChildren;
        }
    }

    public void changeCompany(Company newCompany) {
        if (newCompany != null) {
            this.company = newCompany;
        }
    }

    public void updateAudit(UUID updatedBy) {
        this.audit = this.audit.update(updatedBy);
    }

    public void deleteAudit(UUID deletedBy) {
        this.audit = this.audit.delete(deletedBy);
    }

}
