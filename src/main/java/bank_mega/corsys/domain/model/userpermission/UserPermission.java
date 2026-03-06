package bank_mega.corsys.domain.model.userpermission;

import bank_mega.corsys.domain.model.common.AuditTrail;
import bank_mega.corsys.domain.model.permission.Permission;
import bank_mega.corsys.domain.model.user.User;

/**
 * Domain model for User Permission Override.
 * Allows overriding role-based permissions on a per-user basis.
 */
public class UserPermission {

    private final UserPermissionId id;
    private final User user;
    private final Permission permission;
    private Effect effect;

    public UserPermission(
            UserPermissionId id,
            User user,
            Permission permission,
            Effect effect
    ) {
        this.id = id;
        this.user = user;
        this.permission = permission;
        this.effect = effect;
    }

    public UserPermissionId getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Permission getPermission() {
        return permission;
    }

    public Effect getEffect() {
        return effect;
    }

    public void changeEffect(Effect effect) {
        if (effect != null) {
            this.effect = effect;
        }
    }

    /**
     * Check if this override grants the permission.
     */
    public boolean isAllow() {
        return Effect.ALLOW == this.effect;
    }

    /**
     * Check if this override denies the permission.
     */
    public boolean isDeny() {
        return Effect.DENY == this.effect;
    }

}
