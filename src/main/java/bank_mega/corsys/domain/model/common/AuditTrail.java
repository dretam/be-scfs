package bank_mega.corsys.domain.model.common;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;

import java.time.Instant;
import java.util.UUID;

public record AuditTrail(
        Instant createdAt,
        UUID createdBy,
        Instant updatedAt,
        UUID updatedBy,
        Instant deletedAt,
        UUID deletedBy
) {
    public AuditTrail {
        if (createdAt == null) {
            throw new DomainRuleViolationException("createdAt value cannot be null");
        }

        if (createdBy == null) {
            throw new DomainRuleViolationException("createdBy value cannot be null");
        }
    }

    public static AuditTrail create(UUID createdBy) {
        return new AuditTrail(Instant.now(), createdBy, null, null, null, null);
    }

    public AuditTrail update(UUID updatedBy) {
        return new AuditTrail(this.createdAt, this.createdBy, Instant.now(), updatedBy, null, null);
    }

    public AuditTrail delete(UUID deletedBy) {
        return new AuditTrail(this.createdAt, this.createdBy, this.updatedAt, this.updatedBy, Instant.now(), deletedBy);
    }

}
