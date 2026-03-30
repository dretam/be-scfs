package bank_mega.corsys.domain.model.common;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;

import java.time.Instant;

public record AuditTrail(
        Instant createdAt,
        String createdBy,
        Instant updatedAt,
        String updatedBy,
        Instant deletedAt,
        String deletedBy
) {
    public AuditTrail {
        if (createdAt == null) {
            throw new DomainRuleViolationException("createdAt value cannot be null");
        }

        if (createdBy == null || createdBy.isBlank()) {
            throw new DomainRuleViolationException("createdBy value cannot be null");
        }
    }

    public static AuditTrail create(String createdBy) {
        return new AuditTrail(Instant.now(), createdBy, null, null, null, null);
    }

    public AuditTrail update(String updatedBy) {
        return new AuditTrail(this.createdAt, this.createdBy, Instant.now(), updatedBy, null, null);
    }

    public AuditTrail delete(String deletedBy) {
        return new AuditTrail(this.createdAt, this.createdBy, this.updatedAt, this.updatedBy, Instant.now(), deletedBy);
    }

}
