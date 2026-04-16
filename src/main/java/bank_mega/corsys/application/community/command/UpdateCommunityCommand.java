package bank_mega.corsys.application.community.command;

import bank_mega.corsys.domain.model.community.*;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Builder
public record UpdateCommunityCommand(
        UUID communityId,
        Optional<String> name,
        Optional<String> description,
        Optional<ProductType> productType,
        Optional<Recourse> recourse,
        Optional<HolidayTreatment> holidayTreatment,
        Optional<TransactionFeeType> transactionFeeType,
        Optional<InterestType> interestType,
        Optional<InterestBasis> interestBasis,
        Optional<Boolean> isActive,
        Optional<Boolean> isInterestReserve,
        Optional<Boolean> isFundReserve,
        Optional<Boolean> isSharingIncome,
        Optional<Boolean> isExtendFinancing,
        Optional<BigDecimal> transactionFee,
        Optional<Integer> minFinancing,
        Optional<Integer> maxFinancing,
        Optional<Integer> minTenor,
        Optional<Integer> maxTenor,
        Optional<Integer> gracePeriod,
        Optional<Integer> penaltyRate,
        Optional<EarlyPaymentFeeType> earlyPaymentFeeType,
        Optional<Long> earlyPaymentFeeAmount,
        Optional<List<UpdatePricingTierCommand>> pricingTiers
) {
    @Builder
    public record UpdatePricingTierCommand(
            Optional<UUID> id,
            Optional<PricingTierLogic> logic,
            Optional<BigDecimal> nominal,
            Optional<Integer> quantity
    ) {}
}