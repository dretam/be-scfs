package bank_mega.corsys.application.community.command;

import bank_mega.corsys.domain.model.community.*;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
@Builder
public record CreateCommunityCommand(
        String name,
        String description,
        ProductType productType,
        Recourse recourse,
        HolidayTreatment holidayTreatment,
        TransactionFeeType transactionFeeType,
        InterestType interestType,
        InterestBasis interestBasis,
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
        EarlyPaymentFeeType earlyPaymentFeeType,
        Long earlyPaymentFeeAmount,
        List<PricingTierCommand> pricingTiers
) {
    @Builder
    public record PricingTierCommand(
            PricingTierLogic logic,
            BigDecimal nominal,
            Integer quantity
    ) {}
}
