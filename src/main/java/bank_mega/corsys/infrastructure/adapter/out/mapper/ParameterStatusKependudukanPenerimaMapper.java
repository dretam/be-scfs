package bank_mega.corsys.infrastructure.adapter.out.mapper;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;
import bank_mega.corsys.domain.model.parameter.statuskependudukanpenerima.ParameterStatusKependudukanPenerima;
import bank_mega.corsys.domain.model.parameter.statuskependudukanpenerima.ParameterStatusKependudukanPenerimaCode;
import bank_mega.corsys.domain.model.parameter.statuskependudukanpenerima.ParameterStatusKependudukanPenerimaId;
import bank_mega.corsys.domain.model.parameter.statuskependudukanpenerima.ParameterStatusKependudukanPenerimaValue;
import bank_mega.corsys.infrastructure.adapter.out.jpa.entity.ParameterStatusKependudukanPenerimaJpaEntity;
import jakarta.validation.constraints.NotNull;

public class ParameterStatusKependudukanPenerimaMapper {

    public static ParameterStatusKependudukanPenerima toDomain(@NotNull ParameterStatusKependudukanPenerimaJpaEntity jpaEntity) {
        if (jpaEntity == null) throw new DomainRuleViolationException("JPA Entity is null");

        return new ParameterStatusKependudukanPenerima(
                jpaEntity.getCode() != null ? new ParameterStatusKependudukanPenerimaId(jpaEntity.getCode()) : null,
                new ParameterStatusKependudukanPenerimaCode(jpaEntity.getCode()),
                new ParameterStatusKependudukanPenerimaValue(jpaEntity.getValue()),
                AuditTrailEmbeddableMapper.toDomain(jpaEntity.getAudit())
        );
    }

    public static ParameterStatusKependudukanPenerimaJpaEntity toJpaEntity(ParameterStatusKependudukanPenerima domainEntity) {
        ParameterStatusKependudukanPenerimaJpaEntity jpaEntity = new ParameterStatusKependudukanPenerimaJpaEntity();
        if (domainEntity.getId() != null) {
            jpaEntity.setCode(domainEntity.getId().value());
        }
        jpaEntity.setCode(domainEntity.getCode().value());
        jpaEntity.setValue(domainEntity.getValue().value());

        jpaEntity.setAudit(AuditTrailEmbeddableMapper.toJpa(domainEntity.getAudit()));
        return jpaEntity;
    }

}
