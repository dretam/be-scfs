package bank_mega.corsys.domain.model.userpermission;

/**
 * Effect enum for user permission overrides.
 * ALLOW - Grants permission to the user (overrides role denial)
 * DENY - Denies permission to the user (overrides role grant)
 */
public enum Effect {
    ALLOW,
    DENY
}
