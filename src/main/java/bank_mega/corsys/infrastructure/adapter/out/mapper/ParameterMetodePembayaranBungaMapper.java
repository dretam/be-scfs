package bank_mega.corsys.infrastructure.adapter.out.mapper;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;
import bank_mega.corsys.domain.model.parameter.metodepembayaranbunga.ParameterMetodePembayaranBunga;
import bank_mega.corsys.domain.model.parameter.metodepembayaranbunga.ParameterMetodePembayaranBungaCode;
import bank_mega.corsys.domain.model.parameter.metodepembayaranbunga.ParameterMetodePembayaranBungaId;
import bank_mega.corsys.domain.model.parameter.metodepembayaranbunga.ParameterMetodePembayaranBungaValue;
import bank_mega.corsys.infrastructure.adapter.out.jpa.entity.ParameterMetodePembayaranBungaJpaEntity;
import jakarta.validation.constraints.NotNull;

public class ParameterMetodePembayaranBungaMapper {

    public static ParameterMetodePembayaranBunga toDomain(@NotNull ParameterMetodePembayaranBungaJpaEntity jpaEntity) {
        if (jpaEntity == null) throw new DomainRuleViolationException("JPA Entity is null");

        return new ParameterMetodePembayaranBunga(
                jpaEntity.getCode() != null ? new ParameterMetodePembayaranBungaId(jpaEntity.getCode()) : null,
                new ParameterMetodePembayaranBungaCode(jpaEntity.getCode()),
                new ParameterMetodePembayaranBungaValue(jpaEntity.getValue()),
                AuditTrailEmbeddableMapper.toDomain(jpaEntity.getAudit())
        );
    }

    public static ParameterMetodePembayaranBungaJpaEntity toJpaEntity(ParameterMetodePembayaranBunga domainEntity) {
        ParameterMetodePembayaranBungaJpaEntity jpaEntity = new ParameterMetodePembayaranBungaJpaEntity();
        if (domainEntity.getId() != null) {
            jpaEntity.setCode(domainEntity.getId().value());
        }
        jpaEntity.setCode(domainEntity.getCode().value());
        jpaEntity.setValue(domainEntity.getValue().value());

        jpaEntity.setAudit(AuditTrailEmbeddableMapper.toJpa(domainEntity.getAudit()));
        return jpaEntity;
    }

}
