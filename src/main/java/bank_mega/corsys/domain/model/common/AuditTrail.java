package bank_mega.corsys.domain.model.common;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;

import java.time.Instant;

public record AuditTrail(
        Instant createdAt,
        Long createdBy,
        Instant updatedAt,
        Long updatedBy,
        Instant deletedAt,
        Long deletedBy
) {
    public AuditTrail {
        if (createdAt == null) {
            throw new DomainRuleViolationException("createdAt value cannot be null");
        }

        if (createdBy == null || createdBy <= -1) {
            throw new DomainRuleViolationException("createdBy value cannot be null");
        }
    }

    public static AuditTrail create(Long createdBy) {
        return new AuditTrail(Instant.now(), createdBy, null, null, null, null);
    }

    public AuditTrail update(Long updatedBy) {
        return new AuditTrail(this.createdAt, this.createdBy, Instant.now(), updatedBy, null, null);
    }

    public AuditTrail delete(Long deletedBy) {
        return new AuditTrail(this.createdAt, this.createdBy, this.updatedAt, this.updatedBy, Instant.now(), deletedBy);
    }

}
