package bank_mega.corsys.infrastructure.adapter.out.mapper;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;
import bank_mega.corsys.domain.model.parameter.metodepembayaranpokok.ParameterMetodePembayaranPokok;
import bank_mega.corsys.domain.model.parameter.metodepembayaranpokok.ParameterMetodePembayaranPokokCode;
import bank_mega.corsys.domain.model.parameter.metodepembayaranpokok.ParameterMetodePembayaranPokokId;
import bank_mega.corsys.domain.model.parameter.metodepembayaranpokok.ParameterMetodePembayaranPokokValue;
import bank_mega.corsys.infrastructure.adapter.out.jpa.entity.ParameterMetodePembayaranPokokJpaEntity;
import jakarta.validation.constraints.NotNull;

public class ParameterMetodePembayaranPokokMapper {

    public static ParameterMetodePembayaranPokok toDomain(@NotNull ParameterMetodePembayaranPokokJpaEntity jpaEntity) {
        if (jpaEntity == null) throw new DomainRuleViolationException("JPA Entity is null");

        return new ParameterMetodePembayaranPokok(
                jpaEntity.getCode() != null ? new ParameterMetodePembayaranPokokId(jpaEntity.getCode()) : null,
                new ParameterMetodePembayaranPokokCode(jpaEntity.getCode()),
                new ParameterMetodePembayaranPokokValue(jpaEntity.getValue()),
                AuditTrailEmbeddableMapper.toDomain(jpaEntity.getAudit())
        );
    }

    public static ParameterMetodePembayaranPokokJpaEntity toJpaEntity(ParameterMetodePembayaranPokok domainEntity) {
        ParameterMetodePembayaranPokokJpaEntity jpaEntity = new ParameterMetodePembayaranPokokJpaEntity();
        if (domainEntity.getId() != null) {
            jpaEntity.setCode(domainEntity.getId().value());
        }
        jpaEntity.setCode(domainEntity.getCode().value());
        jpaEntity.setValue(domainEntity.getValue().value());

        jpaEntity.setAudit(AuditTrailEmbeddableMapper.toJpa(domainEntity.getAudit()));
        return jpaEntity;
    }

}
