package bank_mega.corsys.infrastructure.adapter.out.mapper;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;
import bank_mega.corsys.domain.model.parameter.jenistransaksiskn.ParameterJenisTransaksiSkn;
import bank_mega.corsys.domain.model.parameter.jenistransaksiskn.ParameterJenisTransaksiSknCode;
import bank_mega.corsys.domain.model.parameter.jenistransaksiskn.ParameterJenisTransaksiSknId;
import bank_mega.corsys.domain.model.parameter.jenistransaksiskn.ParameterJenisTransaksiSknValue;
import bank_mega.corsys.infrastructure.adapter.out.jpa.entity.ParameterJenisTransaksiSknJpaEntity;
import jakarta.validation.constraints.NotNull;

public class ParameterJenisTransaksiSknMapper {

    public static ParameterJenisTransaksiSkn toDomain(@NotNull ParameterJenisTransaksiSknJpaEntity jpaEntity) {
        if (jpaEntity == null) throw new DomainRuleViolationException("JPA Entity is null");

        return new ParameterJenisTransaksiSkn(
                jpaEntity.getCode() != null ? new ParameterJenisTransaksiSknId(jpaEntity.getCode()) : null,
                new ParameterJenisTransaksiSknCode(jpaEntity.getCode()),
                new ParameterJenisTransaksiSknValue(jpaEntity.getValue()),
                AuditTrailEmbeddableMapper.toDomain(jpaEntity.getAudit())
        );
    }

    public static ParameterJenisTransaksiSknJpaEntity toJpaEntity(ParameterJenisTransaksiSkn domainEntity) {
        ParameterJenisTransaksiSknJpaEntity jpaEntity = new ParameterJenisTransaksiSknJpaEntity();
        if (domainEntity.getId() != null) {
            jpaEntity.setCode(domainEntity.getId().value());
        }
        jpaEntity.setCode(domainEntity.getCode().value());
        jpaEntity.setValue(domainEntity.getValue().value());

        jpaEntity.setAudit(AuditTrailEmbeddableMapper.toJpa(domainEntity.getAudit()));
        return jpaEntity;
    }

}
