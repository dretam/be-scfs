package bank_mega.corsys.infrastructure.adapter.out.mapper;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;
import bank_mega.corsys.domain.model.parameter.jenisperpanjangan.ParameterJenisPerpanjangan;
import bank_mega.corsys.domain.model.parameter.jenisperpanjangan.ParameterJenisPerpanjanganCode;
import bank_mega.corsys.domain.model.parameter.jenisperpanjangan.ParameterJenisPerpanjanganId;
import bank_mega.corsys.domain.model.parameter.jenisperpanjangan.ParameterJenisPerpanjanganValue;
import bank_mega.corsys.infrastructure.adapter.out.jpa.entity.ParameterJenisPerpanjanganJpaEntity;
import jakarta.validation.constraints.NotNull;

public class ParameterJenisPerpanjanganMapper {

    public static ParameterJenisPerpanjangan toDomain(@NotNull ParameterJenisPerpanjanganJpaEntity jpaEntity) {
        if (jpaEntity == null) throw new DomainRuleViolationException("JPA Entity is null");

        return new ParameterJenisPerpanjangan(
                jpaEntity.getCode() != null ? new ParameterJenisPerpanjanganId(jpaEntity.getCode()) : null,
                new ParameterJenisPerpanjanganCode(jpaEntity.getCode()),
                new ParameterJenisPerpanjanganValue(jpaEntity.getValue()),
                AuditTrailEmbeddableMapper.toDomain(jpaEntity.getAudit())
        );
    }

    public static ParameterJenisPerpanjanganJpaEntity toJpaEntity(ParameterJenisPerpanjangan domainEntity) {
        ParameterJenisPerpanjanganJpaEntity jpaEntity = new ParameterJenisPerpanjanganJpaEntity();
        if (domainEntity.getId() != null) {
            jpaEntity.setCode(domainEntity.getId().value());
        }
        jpaEntity.setCode(domainEntity.getCode().value());
        jpaEntity.setValue(domainEntity.getValue().value());

        jpaEntity.setAudit(AuditTrailEmbeddableMapper.toJpa(domainEntity.getAudit()));
        return jpaEntity;
    }

}
