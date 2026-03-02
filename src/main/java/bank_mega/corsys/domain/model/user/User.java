package bank_mega.corsys.domain.model.user;

import bank_mega.corsys.domain.model.common.AuditTrail;
import bank_mega.corsys.domain.model.role.Role;
import lombok.Getter;

public class User {

    private final UserId id;
    private UserName name;
    private UserEmail email;
    private UserPassword password;
    private Role role;
    private UserType type;
    private AuditTrail audit;

    public User(
            UserId id,
            UserName name,
            UserEmail email,
            UserPassword password,
            Role role,
            UserType type,
            AuditTrail audit
    ) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.type = type;
        this.audit = audit;
    }

    public UserId getId() {
        return this.id;
    }

    public UserName getName() {
        return this.name;
    }

    public UserEmail getEmail() {
        return this.email;
    }

    public UserPassword getPassword() {
        return this.password;
    }

    public AuditTrail getAudit() {
        return this.audit;
    }

    public Role getRole() {
        return this.role;
    }

    public UserType getType() {
        return type;
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

    public void updateAudit(Long updatedBy) {
        this.audit = this.audit.update(updatedBy);
    }

    public void deleteAudit(Long deletedBy) {
        this.audit = this.audit.delete(deletedBy);
    }

}
