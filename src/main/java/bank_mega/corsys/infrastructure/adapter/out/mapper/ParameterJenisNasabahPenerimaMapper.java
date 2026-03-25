package bank_mega.corsys.infrastructure.adapter.out.mapper;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;
import bank_mega.corsys.domain.model.parameter.jenisnasabahpenerima.ParameterJenisNasabahPenerima;
import bank_mega.corsys.domain.model.parameter.jenisnasabahpenerima.ParameterJenisNasabahPenerimaCode;
import bank_mega.corsys.domain.model.parameter.jenisnasabahpenerima.ParameterJenisNasabahPenerimaId;
import bank_mega.corsys.domain.model.parameter.jenisnasabahpenerima.ParameterJenisNasabahPenerimaValue;
import bank_mega.corsys.infrastructure.adapter.out.jpa.entity.ParameterJenisNasabahPenerimaJpaEntity;
import jakarta.validation.constraints.NotNull;

public class ParameterJenisNasabahPenerimaMapper {

    public static ParameterJenisNasabahPenerima toDomain(@NotNull ParameterJenisNasabahPenerimaJpaEntity jpaEntity) {
        if (jpaEntity == null) throw new DomainRuleViolationException("JPA Entity is null");

        return new ParameterJenisNasabahPenerima(
                jpaEntity.getCode() != null ? new ParameterJenisNasabahPenerimaId(jpaEntity.getCode()) : null,
                new ParameterJenisNasabahPenerimaCode(jpaEntity.getCode()),
                new ParameterJenisNasabahPenerimaValue(jpaEntity.getValue()),
                AuditTrailEmbeddableMapper.toDomain(jpaEntity.getAudit())
        );
    }

    public static ParameterJenisNasabahPenerimaJpaEntity toJpaEntity(ParameterJenisNasabahPenerima domainEntity) {
        ParameterJenisNasabahPenerimaJpaEntity jpaEntity = new ParameterJenisNasabahPenerimaJpaEntity();
        if (domainEntity.getId() != null) {
            jpaEntity.setCode(domainEntity.getId().value());
        }
        jpaEntity.setCode(domainEntity.getCode().value());
        jpaEntity.setValue(domainEntity.getValue().value());

        jpaEntity.setAudit(AuditTrailEmbeddableMapper.toJpa(domainEntity.getAudit()));
        return jpaEntity;
    }

}
