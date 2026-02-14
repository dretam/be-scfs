package bank_mega.corsys.infrastructure.adapter.out.mapper;

import bank_mega.corsys.domain.model.common.AuditTrail;
import bank_mega.corsys.infrastructure.adapter.out.jpa.entity.embeddable.AuditTrailEmbeddable;

public class AuditTrailEmbeddableMapper {

    public static AuditTrail toDomain(AuditTrailEmbeddable jpaEntity) {
        if (jpaEntity == null) {
            return null;
        }

        return new AuditTrail(
                jpaEntity.getCreatedAt(),
                jpaEntity.getCreatedBy(),
                jpaEntity.getUpdatedAt(),
                jpaEntity.getUpdatedBy(),
                jpaEntity.getDeletedAt(),
                jpaEntity.getDeletedBy()
        );
    }

    public static AuditTrailEmbeddable toJpa(AuditTrail domainEntity) {
        if (domainEntity == null) {
            return null;
        }

        AuditTrailEmbeddable jpaEntity = new AuditTrailEmbeddable();
        jpaEntity.setCreatedAt(domainEntity.createdAt());
        jpaEntity.setCreatedBy(domainEntity.createdBy());
        jpaEntity.setUpdatedAt(domainEntity.updatedAt());
        jpaEntity.setUpdatedBy(domainEntity.updatedBy());
        jpaEntity.setDeletedAt(domainEntity.deletedAt());
        jpaEntity.setDeletedBy(domainEntity.deletedBy());
        return jpaEntity;
    }

}
