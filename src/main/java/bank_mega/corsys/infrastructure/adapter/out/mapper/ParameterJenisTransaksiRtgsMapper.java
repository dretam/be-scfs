package bank_mega.corsys.infrastructure.adapter.out.mapper;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;
import bank_mega.corsys.domain.model.parameter.jenistransaksirtgs.ParameterJenisTransaksiRtgs;
import bank_mega.corsys.domain.model.parameter.jenistransaksirtgs.ParameterJenisTransaksiRtgsCode;
import bank_mega.corsys.domain.model.parameter.jenistransaksirtgs.ParameterJenisTransaksiRtgsId;
import bank_mega.corsys.domain.model.parameter.jenistransaksirtgs.ParameterJenisTransaksiRtgsValue;
import bank_mega.corsys.infrastructure.adapter.out.jpa.entity.ParameterJenisTransaksiRtgsJpaEntity;
import jakarta.validation.constraints.NotNull;

public class ParameterJenisTransaksiRtgsMapper {

    public static ParameterJenisTransaksiRtgs toDomain(@NotNull ParameterJenisTransaksiRtgsJpaEntity jpaEntity) {
        if (jpaEntity == null) throw new DomainRuleViolationException("JPA Entity is null");

        return new ParameterJenisTransaksiRtgs(
                jpaEntity.getCode() != null ? new ParameterJenisTransaksiRtgsId(jpaEntity.getCode()) : null,
                new ParameterJenisTransaksiRtgsCode(jpaEntity.getCode()),
                new ParameterJenisTransaksiRtgsValue(jpaEntity.getValue()),
                AuditTrailEmbeddableMapper.toDomain(jpaEntity.getAudit())
        );
    }

    public static ParameterJenisTransaksiRtgsJpaEntity toJpaEntity(ParameterJenisTransaksiRtgs domainEntity) {
        ParameterJenisTransaksiRtgsJpaEntity jpaEntity = new ParameterJenisTransaksiRtgsJpaEntity();
        if (domainEntity.getId() != null) {
            jpaEntity.setCode(domainEntity.getId().value());
        }
        jpaEntity.setCode(domainEntity.getCode().value());
        jpaEntity.setValue(domainEntity.getValue().value());

        jpaEntity.setAudit(AuditTrailEmbeddableMapper.toJpa(domainEntity.getAudit()));
        return jpaEntity;
    }

}
