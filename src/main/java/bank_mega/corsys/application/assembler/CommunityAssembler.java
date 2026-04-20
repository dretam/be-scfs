package bank_mega.corsys.application.assembler;

import bank_mega.corsys.application.community.dto.CommunityResponse;
import bank_mega.corsys.domain.model.community.Community;
import bank_mega.corsys.domain.model.community.CommunityPricingTier;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommunityAssembler {

    public static CommunityResponse toResponse(Community community) {
        if (community == null) return null;

        List<CommunityResponse.PricingTierResponse> pricingTierResponses = buildPricingTierResponses(community.getPricingTiers());

        return CommunityResponse.builder()
                .communityId(community.getCommunityId().value())
                .name(community.getCommunityName().value())
                .description(community.getCommunityDescription().value())
                .productType(community.getProductType().name())
                .recourse(community.getRecourse().name())
                .holidayTreatment(community.getHolidayTreatment().name())
                .transactionFeeType(community.getTransactionFeeType().name())
                .interestType(community.getInterestType().name())
                .interestBasis(community.getInterestBasis().name())
                .isActive(community.getCommunityIsActive().value())
                .isInterestReserve(community.getCommunityIsInterestReserve().value())
                .isFundReserve(community.getCommunityIsFundReserve().value())
                .isSharingIncome(community.getCommunityIsSharingIncome().value())
                .isExtendFinancing(community.getCommunityIsExtendFinancing().value())
                .transactionFee(community.getCommunityTransactionFee().value())
                .minFinancing(community.getCommunityMinFinancing().value())
                .maxFinancing(community.getCommunityMaxFinancing().value())
                .minTenor(community.getCommunityMinTenor().value())
                .maxTenor(community.getCommunityMaxTenor().value())
                .gracePeriod(community.getCommunityGracePeriod().value())
                .penaltyRate(community.getCommunityPenaltyRate().value())
                .earlyPaymentFeeType(community.getEarlyPaymentFeeType().name())
                .earlyPaymentFeeAmount(community.getCommunityEarlyPaymentFeeAmount().value())
                .pricingTiers(pricingTierResponses)  // Changed from pricingTier (singular) to pricingTiers (plural)
                .createdAt(community.getAudit().createdAt())
                .createdBy(community.getAudit().createdBy())
                .updatedAt(community.getAudit().updatedAt())
                .updatedBy(community.getAudit().updatedBy())
                .deletedAt(community.getAudit().deletedAt())
                .deletedBy(community.getAudit().deletedBy())
                .build();
    }

    private static List<CommunityResponse.PricingTierResponse> buildPricingTierResponses(Set<CommunityPricingTier> pricingTiers) {
        if (pricingTiers == null || pricingTiers.isEmpty()) {
            return Collections.emptyList();
        }

        return pricingTiers.stream()
                .map(CommunityAssembler::toPricingTierResponse)
                .collect(Collectors.toList());
    }

    private static CommunityResponse.PricingTierResponse toPricingTierResponse(CommunityPricingTier pt) {
        return CommunityResponse.PricingTierResponse.builder()
                .id(pt.getId().value())
                .logic(pt.getLogic().name())
                .nominal(pt.getNominal().value())
                .quantity(pt.getQuantity().value())
                .build();
    }
}