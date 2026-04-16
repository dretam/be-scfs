package bank_mega.corsys.application.community.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Builder
public record CommunityResponse(
        UUID communityId,
        String name,
        String description,
        String productType,
        String recourse,
        String holidayTreatment,
        String transactionFeeType,
        String interestType,
        String interestBasis,
        Boolean isActive,
        Boolean isInterestReserve,
        Boolean isFundReserve,
        Boolean isSharingIncome,
        Boolean isExtendFinancing,
        BigDecimal transactionFee,
        Integer minFinancing,
        Integer maxFinancing,
        Integer minTenor,
        Integer maxTenor,
        Integer gracePeriod,
        Integer penaltyRate,
        String earlyPaymentFeeType,
        Long earlyPaymentFeeAmount,
        PricingTierResponse pricingTier,
        Instant createdAt,
        UUID createdBy,
        Instant updatedAt,
        UUID updatedBy,
        Instant deletedAt,
        UUID deletedBy
) {
    @Builder
    public record PricingTierResponse(
            UUID id,
            String logic,
            BigDecimal nominal,
            Integer quantity
    ) {}
}
