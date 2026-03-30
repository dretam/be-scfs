package bank_mega.corsys.infrastructure.adapter.out.mapper;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;
import bank_mega.corsys.domain.model.company.*;
import bank_mega.corsys.infrastructure.adapter.out.jpa.entity.CompanyJpaEntity;
import jakarta.validation.constraints.NotNull;

public class CompanyMapper {

    public static Company toDomain(@NotNull CompanyJpaEntity jpaEntity) {
        if (jpaEntity == null) throw new DomainRuleViolationException("JPA Entity is null");
        return new Company(
                new CompanyId(jpaEntity.getId()),
                new CompanyCif(jpaEntity.getCif()),
                new CompanyName(jpaEntity.getName()),
                jpaEntity.getType(),
                new CompanyRmUserId(jpaEntity.getRmUserId()),
                new CompanyDiscountRate(jpaEntity.getDiscountRate()),
                new CompanyMaxFinancing(jpaEntity.getMaxFinancing()),
                AuditTrailEmbeddableMapper.toDomain(jpaEntity.getAudit())
        );
    }

    public static CompanyJpaEntity toJpaEntity(Company domainEntity) {
        CompanyJpaEntity jpaEntity = new CompanyJpaEntity();
        if (domainEntity.getCompanyId() != null) {
            jpaEntity.setId(domainEntity.getCompanyId().value());
        }
        jpaEntity.setCif(domainEntity.getCompanyCif().value());
        jpaEntity.setName(domainEntity.getCompanyName().value());
        jpaEntity.setType(domainEntity.getCompanyType());
        jpaEntity.setRmUserId(domainEntity.getCompanyRmUserId().value());
        jpaEntity.setDiscountRate(domainEntity.getCompanyDiscountRate().value());
        jpaEntity.setMaxFinancing(domainEntity.getCompanyMaxFinancing().value());
        jpaEntity.setAudit(AuditTrailEmbeddableMapper.toJpa(domainEntity.getAudit()));
        return jpaEntity;
    }

}
