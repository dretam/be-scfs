package bank_mega.corsys.application.assembler;

import bank_mega.corsys.application.community.dto.CommunityResponse;
import bank_mega.corsys.domain.model.community.Community;
import bank_mega.corsys.domain.model.community.CommunityPricingTier;

public class CommunityAssembler {
    public static CommunityResponse toResponse(Community saved) {
        if (saved == null) return null;
        
        CommunityResponse.PricingTierResponse pricingTierResponse = null;
        if (saved.getPricingTier() != null) {
            CommunityPricingTier pt = saved.getPricingTier();
            pricingTierResponse = CommunityResponse.PricingTierResponse.builder()
                    .id(pt.getId().value())
                    .logic(pt.getLogic().name())
                    .nominal(pt.getNominal().value())
                    .quantity(pt.getQuantity().value())
                    .build();
        }

        return CommunityResponse.builder()
                .communityId(saved.getCommunityId().value())
                .name(saved.getCommunityName().value())
                .description(saved.getCommunityDescription().value())
                .productType(saved.getProductType().name())
                .recourse(saved.getRecourse().name())
                .holidayTreatment(saved.getHolidayTreatment().name())
                .transactionFeeType(saved.getTransactionFeeType().name())
                .interestType(saved.getInterestType().name())
                .interestBasis(saved.getInterestBasis().name())
                .isActive(saved.getCommunityIsActive().value())
                .isInterestReserve(saved.getCommunityIsInterestReserve().value())
                .isFundReserve(saved.getCommunityIsFundReserve().value())
                .isSharingIncome(saved.getCommunityIsSharingIncome().value())
                .isExtendFinancing(saved.getCommunityIsExtendFinancing().value())
                .transactionFee(saved.getCommunityTransactionFee().value())
                .minFinancing(saved.getCommunityMinFinancing().value())
                .maxFinancing(saved.getCommunityMaxFinancing().value())
                .minTenor(saved.getCommunityMinTenor().value())
                .maxTenor(saved.getCommunityMaxTenor().value())
                .gracePeriod(saved.getCommunityGracePeriod().value())
                .penaltyRate(saved.getCommunityPenaltyRate().value())
                .earlyPaymentFeeType(saved.getEarlyPaymentFeeType().name())
                .earlyPaymentFeeAmount(saved.getCommunityEarlyPaymentFeeAmount().value())
                .pricingTier(pricingTierResponse)
                .createdAt(saved.getAudit().createdAt())
                .createdBy(saved.getAudit().createdBy())
                .updatedAt(saved.getAudit().updatedAt())
                .updatedBy(saved.getAudit().updatedBy())
                .deletedAt(saved.getAudit().deletedAt())
                .deletedBy(saved.getAudit().deletedBy())
                .build();
    }
}
