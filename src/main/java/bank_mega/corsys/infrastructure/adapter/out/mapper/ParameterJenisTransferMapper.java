package bank_mega.corsys.infrastructure.adapter.out.mapper;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;
import bank_mega.corsys.domain.model.parameter.jenistransfer.ParameterJenisTransfer;
import bank_mega.corsys.domain.model.parameter.jenistransfer.ParameterJenisTransferCode;
import bank_mega.corsys.domain.model.parameter.jenistransfer.ParameterJenisTransferId;
import bank_mega.corsys.domain.model.parameter.jenistransfer.ParameterJenisTransferValue;
import bank_mega.corsys.infrastructure.adapter.out.jpa.entity.ParameterJenisTransferJpaEntity;
import jakarta.validation.constraints.NotNull;

public class ParameterJenisTransferMapper {

    public static ParameterJenisTransfer toDomain(@NotNull ParameterJenisTransferJpaEntity jpaEntity) {
        if (jpaEntity == null) throw new DomainRuleViolationException("JPA Entity is null");

        return new ParameterJenisTransfer(
                jpaEntity.getCode() != null ? new ParameterJenisTransferId(jpaEntity.getCode()) : null,
                new ParameterJenisTransferCode(jpaEntity.getCode()),
                new ParameterJenisTransferValue(jpaEntity.getValue()),
                AuditTrailEmbeddableMapper.toDomain(jpaEntity.getAudit())
        );
    }

    public static ParameterJenisTransferJpaEntity toJpaEntity(ParameterJenisTransfer domainEntity) {
        ParameterJenisTransferJpaEntity jpaEntity = new ParameterJenisTransferJpaEntity();
        if (domainEntity.getId() != null) {
            jpaEntity.setCode(domainEntity.getId().value());
        }
        jpaEntity.setCode(domainEntity.getCode().value());
        jpaEntity.setValue(domainEntity.getValue().value());

        jpaEntity.setAudit(AuditTrailEmbeddableMapper.toJpa(domainEntity.getAudit()));
        return jpaEntity;
    }

}
