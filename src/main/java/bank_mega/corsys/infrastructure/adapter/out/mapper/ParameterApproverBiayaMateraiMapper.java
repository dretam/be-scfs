package bank_mega.corsys.infrastructure.adapter.out.mapper;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;
import bank_mega.corsys.domain.model.parameter.approverbiayamaterai.ParameterApproverBiayaMaterai;
import bank_mega.corsys.domain.model.parameter.approverbiayamaterai.ParameterApproverBiayaMateraiCode;
import bank_mega.corsys.domain.model.parameter.approverbiayamaterai.ParameterApproverBiayaMateraiId;
import bank_mega.corsys.domain.model.parameter.approverbiayamaterai.ParameterApproverBiayaMateraiValue;
import bank_mega.corsys.infrastructure.adapter.out.jpa.entity.ParameterApproverBiayaMateraiJpaEntity;
import jakarta.validation.constraints.NotNull;

public class ParameterApproverBiayaMateraiMapper {

    public static ParameterApproverBiayaMaterai toDomain(@NotNull ParameterApproverBiayaMateraiJpaEntity jpaEntity) {
        if (jpaEntity == null) throw new DomainRuleViolationException("JPA Entity is null");

        return new ParameterApproverBiayaMaterai(
                jpaEntity.getCode() != null ? new ParameterApproverBiayaMateraiId(jpaEntity.getCode()) : null,
                new ParameterApproverBiayaMateraiCode(jpaEntity.getCode()),
                new ParameterApproverBiayaMateraiValue(jpaEntity.getValue()),
                AuditTrailEmbeddableMapper.toDomain(jpaEntity.getAudit())
        );
    }

    public static ParameterApproverBiayaMateraiJpaEntity toJpaEntity(ParameterApproverBiayaMaterai domainEntity) {
        ParameterApproverBiayaMateraiJpaEntity jpaEntity = new ParameterApproverBiayaMateraiJpaEntity();
        if (domainEntity.getId() != null) {
            jpaEntity.setCode(domainEntity.getId().value());
        }
        jpaEntity.setCode(domainEntity.getCode().value());
        jpaEntity.setValue(domainEntity.getValue().value());

        jpaEntity.setAudit(AuditTrailEmbeddableMapper.toJpa(domainEntity.getAudit()));
        return jpaEntity;
    }

}
