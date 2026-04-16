package bank_mega.corsys.infrastructure.adapter.out.mapper;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;
import bank_mega.corsys.domain.model.community.*;
import bank_mega.corsys.infrastructure.adapter.out.jpa.entity.CommunityJpaEntity;
import bank_mega.corsys.infrastructure.adapter.out.jpa.entity.CommunityPricingTierJpaEntity;
import jakarta.validation.constraints.NotNull;

import java.util.Set;
import java.util.stream.Collectors;

public class CommunityMapper {

    public static Community toDomain(@NotNull CommunityJpaEntity jpaEntity) {
        if (jpaEntity == null) throw new DomainRuleViolationException("JPA Entity is null (CommunityMapper)");

        Set<CommunityPricingTier> domainPricingTiers = toDomainPricingTiers(jpaEntity.getPricingTiers());

        return new Community(
                new CommunityId(jpaEntity.getId()),
                new CommunityName(jpaEntity.getName()),
                new CommunityDescription(jpaEntity.getDescription()),
                jpaEntity.getProductType(),
                jpaEntity.getRecourse(),
                jpaEntity.getHolidayTreatment(),
                jpaEntity.getTransactionFeeType(),
                jpaEntity.getInterestType(),
                jpaEntity.getInterestBasis(),
                new CommunityIsActive(jpaEntity.getIsActive()),
                new CommunityIsInterestReserve(jpaEntity.getIsInterestReserve()),
                new CommunityIsFundReserve(jpaEntity.getIsFundReserve()),
                new CommunityIsSharingIncome(jpaEntity.getIsSharingIncome()),
                new CommunityIsExtendFinancing(jpaEntity.getIsExtendFinancing()),
                new CommunityTransactionFee(jpaEntity.getTransactionFee()),
                new CommunityMinFinancing(jpaEntity.getMinFinancing()),
                new CommunityMaxFinancing(jpaEntity.getMaxFinancing()),
                new CommunityMinTenor(jpaEntity.getMinTenor()),
                new CommunityMaxTenor(jpaEntity.getMaxTenor()),
                new CommunityGracePeriod(jpaEntity.getGracePeriod()),
                new CommunityPenaltyRate(jpaEntity.getPenaltyRate()),
                jpaEntity.getEarlyPaymentFeeType(),
                new CommunityEarlyPaymentFeeAmount(jpaEntity.getEarlyPaymentFeeAmount()),
                domainPricingTiers,
                AuditTrailEmbeddableMapper.toDomain(jpaEntity.getAudit())
        );
    }

    public static CommunityJpaEntity toJpaEntity(Community domainEntity) {
        CommunityJpaEntity jpaEntity = new CommunityJpaEntity();
        if (domainEntity.getCommunityId() != null) {
            jpaEntity.setId(domainEntity.getCommunityId().value());
        }
        jpaEntity.setName(domainEntity.getCommunityName().value());
        jpaEntity.setDescription(domainEntity.getCommunityDescription().value());
        jpaEntity.setProductType(domainEntity.getProductType());
        jpaEntity.setRecourse(domainEntity.getRecourse());
        jpaEntity.setHolidayTreatment(domainEntity.getHolidayTreatment());
        jpaEntity.setTransactionFeeType(domainEntity.getTransactionFeeType());
        jpaEntity.setInterestType(domainEntity.getInterestType());
        jpaEntity.setInterestBasis(domainEntity.getInterestBasis());
        jpaEntity.setIsActive(domainEntity.getCommunityIsActive().value());
        jpaEntity.setIsInterestReserve(domainEntity.getCommunityIsInterestReserve().value());
        jpaEntity.setIsFundReserve(domainEntity.getCommunityIsFundReserve().value());
        jpaEntity.setIsSharingIncome(domainEntity.getCommunityIsSharingIncome().value());
        jpaEntity.setIsExtendFinancing(domainEntity.getCommunityIsExtendFinancing().value());
        jpaEntity.setTransactionFee(domainEntity.getCommunityTransactionFee().value());
        jpaEntity.setMinFinancing(domainEntity.getCommunityMinFinancing().value());
        jpaEntity.setMaxFinancing(domainEntity.getCommunityMaxFinancing().value());
        jpaEntity.setMinTenor(domainEntity.getCommunityMinTenor().value());
        jpaEntity.setMaxTenor(domainEntity.getCommunityMaxTenor().value());
        jpaEntity.setGracePeriod(domainEntity.getCommunityGracePeriod().value());
        jpaEntity.setPenaltyRate(domainEntity.getCommunityPenaltyRate().value());
        jpaEntity.setEarlyPaymentFeeType(domainEntity.getEarlyPaymentFeeType());
        jpaEntity.setEarlyPaymentFeeAmount(domainEntity.getCommunityEarlyPaymentFeeAmount().value());

        Set<CommunityPricingTierJpaEntity> pricingTiers = toJpaPricingTiers(domainEntity.getPricingTiers());
        pricingTiers.forEach(tier -> tier.setCommunity(jpaEntity));
        jpaEntity.setPricingTiers(pricingTiers);

        jpaEntity.setAudit(AuditTrailEmbeddableMapper.toJpa(domainEntity.getAudit()));
        return jpaEntity;
    }

    private static Set<CommunityPricingTier> toDomainPricingTiers(Set<CommunityPricingTierJpaEntity> jpaEntities) {
        if (jpaEntities == null) return Set.of();
        return jpaEntities.stream()
                .map(CommunityMapper::toDomainPricingTier)
                .collect(Collectors.toSet());
    }

    private static Set<CommunityPricingTierJpaEntity> toJpaPricingTiers(Set<CommunityPricingTier> domainEntities) {
        if (domainEntities == null) return Set.of();
        return domainEntities.stream()
                .map(CommunityMapper::toJpaPricingTier)
                .collect(Collectors.toSet());
    }

    private static CommunityPricingTier toDomainPricingTier(CommunityPricingTierJpaEntity jpaEntity) {
        return new CommunityPricingTier(
                new PricingTierId(jpaEntity.getId()),
                jpaEntity.getLogic(),
                new PricingTierNominal(jpaEntity.getNominal()),
                new PricingTierQuantity(jpaEntity.getQuantity()),
                AuditTrailEmbeddableMapper.toDomain(jpaEntity.getAudit())
        );
    }

    private static CommunityPricingTierJpaEntity toJpaPricingTier(CommunityPricingTier domainEntity) {
        CommunityPricingTierJpaEntity jpaEntity = new CommunityPricingTierJpaEntity();
        if (domainEntity.getId() != null) {
            jpaEntity.setId(domainEntity.getId().value());
        }
        jpaEntity.setLogic(domainEntity.getLogic());
        jpaEntity.setNominal(domainEntity.getNominal().value());
        jpaEntity.setQuantity(domainEntity.getQuantity().value());
        jpaEntity.setAudit(AuditTrailEmbeddableMapper.toJpa(domainEntity.getAudit()));
        return jpaEntity;
    }
}