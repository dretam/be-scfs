package bank_mega.corsys.domain.model.forgotpasstoken;

import bank_mega.corsys.domain.model.common.AuditTrail;
import bank_mega.corsys.domain.model.user.User;

import java.time.Instant;
import java.util.UUID;

public class ForgotToken {

    private final ForgotTokenId forgotTokenId;
    private User user;
    private ForgotTokenHash forgotTokenHash;
    private Instant expiresAt;
    private ForgotTokenUsed forgotTokenUsed;
    private AuditTrail audit;

    public ForgotToken(
            ForgotTokenId forgotTokenId,
            User user,
            ForgotTokenHash forgotTokenHash,
            Instant expiresAt,
            ForgotTokenUsed forgotTokenUsed,
            AuditTrail audit
    ) {
        this.forgotTokenId = forgotTokenId;
        this.user = user;
        this.forgotTokenHash = forgotTokenHash;
        this.expiresAt = expiresAt;
        this.forgotTokenUsed = forgotTokenUsed;
        this.audit = audit;
    }

    public AuditTrail getAudit() {
        return audit;
    }

    public ForgotTokenId getForgotTokenId() {
        return forgotTokenId;
    }

    public User getUser() {
        return user;
    }

    public ForgotTokenHash getForgotTokenHash() {
        return forgotTokenHash;
    }

    public Instant getExpiresAt() {
        return expiresAt;
    }

    public ForgotTokenUsed getForgotTokenUsed() {
        return forgotTokenUsed;
    }

    public void updateUsed(Boolean used) {
        this.forgotTokenUsed = new ForgotTokenUsed(used);
    }

    public void updateAudit(UUID updatedBy) {
        this.audit = this.audit.update(updatedBy);
    }

    public void deleteAudit(UUID deletedBy) {
        this.audit = this.audit.delete(deletedBy);
    }

    public boolean isExpired() {
        return Instant.now().isAfter(expiresAt);
    }
}
