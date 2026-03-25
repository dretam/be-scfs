package bank_mega.corsys.infrastructure.adapter.out.mapper;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;
import bank_mega.corsys.domain.model.parameter.sumberdana.ParameterSumberDana;
import bank_mega.corsys.domain.model.parameter.sumberdana.ParameterSumberDanaCode;
import bank_mega.corsys.domain.model.parameter.sumberdana.ParameterSumberDanaId;
import bank_mega.corsys.domain.model.parameter.sumberdana.ParameterSumberDanaValue;
import bank_mega.corsys.infrastructure.adapter.out.jpa.entity.ParameterSumberDanaJpaEntity;
import jakarta.validation.constraints.NotNull;

public class ParameterSumberDanaMapper {

    public static ParameterSumberDana toDomain(@NotNull ParameterSumberDanaJpaEntity jpaEntity) {
        if (jpaEntity == null) throw new DomainRuleViolationException("JPA Entity is null");

        return new ParameterSumberDana(
                jpaEntity.getCode() != null ? new ParameterSumberDanaId(jpaEntity.getCode()) : null,
                new ParameterSumberDanaCode(jpaEntity.getCode()),
                new ParameterSumberDanaValue(jpaEntity.getValue()),
                AuditTrailEmbeddableMapper.toDomain(jpaEntity.getAudit())
        );
    }

    public static ParameterSumberDanaJpaEntity toJpaEntity(ParameterSumberDana domainEntity) {
        ParameterSumberDanaJpaEntity jpaEntity = new ParameterSumberDanaJpaEntity();
        if (domainEntity.getId() != null) {
            jpaEntity.setCode(domainEntity.getId().value());
        }
        jpaEntity.setCode(domainEntity.getCode().value());
        jpaEntity.setValue(domainEntity.getValue().value());

        jpaEntity.setAudit(AuditTrailEmbeddableMapper.toJpa(domainEntity.getAudit()));
        return jpaEntity;
    }

}
