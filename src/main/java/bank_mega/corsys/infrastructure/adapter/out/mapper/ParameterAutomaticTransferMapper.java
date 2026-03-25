package bank_mega.corsys.infrastructure.adapter.out.mapper;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;
import bank_mega.corsys.domain.model.parameter.automatictransfer.ParameterAutomaticTransfer;
import bank_mega.corsys.domain.model.parameter.automatictransfer.ParameterAutomaticTransferCode;
import bank_mega.corsys.domain.model.parameter.automatictransfer.ParameterAutomaticTransferId;
import bank_mega.corsys.domain.model.parameter.automatictransfer.ParameterAutomaticTransferValue;
import bank_mega.corsys.infrastructure.adapter.out.jpa.entity.ParameterAutomaticTransferJpaEntity;
import jakarta.validation.constraints.NotNull;

public class ParameterAutomaticTransferMapper {

    public static ParameterAutomaticTransfer toDomain(@NotNull ParameterAutomaticTransferJpaEntity jpaEntity) {
        if (jpaEntity == null) throw new DomainRuleViolationException("JPA Entity is null");

        return new ParameterAutomaticTransfer(
                jpaEntity.getCode() != null ? new ParameterAutomaticTransferId(jpaEntity.getCode()) : null,
                new ParameterAutomaticTransferCode(jpaEntity.getCode()),
                new ParameterAutomaticTransferValue(jpaEntity.getValue()),
                AuditTrailEmbeddableMapper.toDomain(jpaEntity.getAudit())
        );
    }

    public static ParameterAutomaticTransferJpaEntity toJpaEntity(ParameterAutomaticTransfer domainEntity) {
        ParameterAutomaticTransferJpaEntity jpaEntity = new ParameterAutomaticTransferJpaEntity();
        if (domainEntity.getId() != null) {
            jpaEntity.setCode(domainEntity.getId().value());
        }
        jpaEntity.setCode(domainEntity.getCode().value());
        jpaEntity.setValue(domainEntity.getValue().value());

        jpaEntity.setAudit(AuditTrailEmbeddableMapper.toJpa(domainEntity.getAudit()));
        return jpaEntity;
    }

}
