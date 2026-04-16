package bank_mega.corsys.domain.model.community;

import bank_mega.corsys.domain.model.common.AuditTrail;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
public class Community {
    private final CommunityId communityId;
    private CommunityName communityName;
    private CommunityDescription communityDescription;
    private ProductType productType;
    private Recourse recourse;
    private HolidayTreatment holidayTreatment;
    private TransactionFeeType transactionFeeType;
    private InterestType interestType;
    private InterestBasis interestBasis;
    private CommunityIsActive communityIsActive;
    private CommunityIsInterestReserve communityIsInterestReserve;
    private CommunityIsFundReserve communityIsFundReserve;
    private CommunityIsSharingIncome communityIsSharingIncome;
    private CommunityIsExtendFinancing communityIsExtendFinancing;
    private CommunityTransactionFee communityTransactionFee;
    private CommunityMinFinancing communityMinFinancing;
    private CommunityMaxFinancing communityMaxFinancing;
    private CommunityMinTenor communityMinTenor;
    private CommunityMaxTenor communityMaxTenor;
    private CommunityGracePeriod communityGracePeriod;
    private CommunityPenaltyRate communityPenaltyRate;
    private EarlyPaymentFeeType earlyPaymentFeeType;
    private CommunityEarlyPaymentFeeAmount communityEarlyPaymentFeeAmount;
    private Set<CommunityPricingTier> pricingTiers;
    private AuditTrail audit;

    public Community(
            CommunityId communityId,
            CommunityName communityName,
            CommunityDescription communityDescription,
            ProductType productType,
            Recourse recourse,
            HolidayTreatment holidayTreatment,
            TransactionFeeType transactionFeeType,
            InterestType interestType,
            InterestBasis interestBasis,
            CommunityIsActive communityIsActive,
            CommunityIsInterestReserve communityIsInterestReserve,
            CommunityIsFundReserve communityIsFundReserve,
            CommunityIsSharingIncome communityIsSharingIncome,
            CommunityIsExtendFinancing communityIsExtendFinancing,
            CommunityTransactionFee communityTransactionFee,
            CommunityMinFinancing communityMinFinancing,
            CommunityMaxFinancing communityMaxFinancing,
            CommunityMinTenor communityMinTenor,
            CommunityMaxTenor communityMaxTenor,
            CommunityGracePeriod communityGracePeriod,
            CommunityPenaltyRate communityPenaltyRate,
            EarlyPaymentFeeType earlyPaymentFeeType,
            CommunityEarlyPaymentFeeAmount communityEarlyPaymentFeeAmount,
            Set<CommunityPricingTier> pricingTiers,
            AuditTrail audit
    ) {
        this.communityId = communityId;
        this.communityName = communityName;
        this.communityDescription = communityDescription;
        this.productType = productType;
        this.recourse = recourse;
        this.holidayTreatment = holidayTreatment;
        this.transactionFeeType = transactionFeeType;
        this.interestType = interestType;
        this.interestBasis = interestBasis;
        this.communityIsActive = communityIsActive;
        this.communityIsInterestReserve = communityIsInterestReserve;
        this.communityIsFundReserve = communityIsFundReserve;
        this.communityIsSharingIncome = communityIsSharingIncome;
        this.communityIsExtendFinancing = communityIsExtendFinancing;
        this.communityTransactionFee = communityTransactionFee;
        this.communityMinFinancing = communityMinFinancing;
        this.communityMaxFinancing = communityMaxFinancing;
        this.communityMinTenor = communityMinTenor;
        this.communityMaxTenor = communityMaxTenor;
        this.communityGracePeriod = communityGracePeriod;
        this.communityPenaltyRate = communityPenaltyRate;
        this.earlyPaymentFeeType = earlyPaymentFeeType;
        this.communityEarlyPaymentFeeAmount = communityEarlyPaymentFeeAmount;
        this.pricingTiers = pricingTiers != null ? pricingTiers : new HashSet<>();
        this.audit = audit;
    }

    public void changeCommunityName(CommunityName name) {
        if (name != null) this.communityName = name;
    }

    public void changeCommunityDescription(CommunityDescription description) {
        if (description != null) this.communityDescription = description;
    }

    public void changeProductType(ProductType productType) {
        if (productType != null) this.productType = productType;
    }

    public void changeRecourse(Recourse recourse) {
        if (recourse != null) this.recourse = recourse;
    }

    public void changeHolidayTreatment(HolidayTreatment holidayTreatment) {
        if (holidayTreatment != null) this.holidayTreatment = holidayTreatment;
    }

    public void changeTransactionFeeType(TransactionFeeType transactionFeeType) {
        if (transactionFeeType != null) this.transactionFeeType = transactionFeeType;
    }

    public void changeInterestType(InterestType interestType) {
        if (interestType != null) this.interestType = interestType;
    }

    public void changeInterestBasis(InterestBasis interestBasis) {
        if (interestBasis != null) this.interestBasis = interestBasis;
    }

    public void changeCommunityIsActive(CommunityIsActive isActive) {
        if (isActive != null) this.communityIsActive = isActive;
    }

    public void changeCommunityIsInterestReserve(CommunityIsInterestReserve isInterestReserve) {
        if (isInterestReserve != null) this.communityIsInterestReserve = isInterestReserve;
    }

    public void changeCommunityIsFundReserve(CommunityIsFundReserve isFundReserve) {
        if (isFundReserve != null) this.communityIsFundReserve = isFundReserve;
    }

    public void changeCommunityIsSharingIncome(CommunityIsSharingIncome isSharingIncome) {
        if (isSharingIncome != null) this.communityIsSharingIncome = isSharingIncome;
    }

    public void changeCommunityIsExtendFinancing(CommunityIsExtendFinancing isExtendFinancing) {
        if (isExtendFinancing != null) this.communityIsExtendFinancing = isExtendFinancing;
    }

    public void changeCommunityTransactionFee(CommunityTransactionFee transactionFee) {
        if (transactionFee != null) this.communityTransactionFee = transactionFee;
    }

    public void changeCommunityMinFinancing(CommunityMinFinancing minFinancing) {
        if (minFinancing != null) this.communityMinFinancing = minFinancing;
    }

    public void changeCommunityMaxFinancing(CommunityMaxFinancing maxFinancing) {
        if (maxFinancing != null) this.communityMaxFinancing = maxFinancing;
    }

    public void changeCommunityMinTenor(CommunityMinTenor minTenor) {
        if (minTenor != null) this.communityMinTenor = minTenor;
    }

    public void changeCommunityMaxTenor(CommunityMaxTenor maxTenor) {
        if (maxTenor != null) this.communityMaxTenor = maxTenor;
    }

    public void changeCommunityGracePeriod(CommunityGracePeriod gracePeriod) {
        if (gracePeriod != null) this.communityGracePeriod = gracePeriod;
    }

    public void changeCommunityPenaltyRate(CommunityPenaltyRate penaltyRate) {
        if (penaltyRate != null) this.communityPenaltyRate = penaltyRate;
    }

    public void changeEarlyPaymentFeeType(EarlyPaymentFeeType earlyPaymentFeeType) {
        if (earlyPaymentFeeType != null) this.earlyPaymentFeeType = earlyPaymentFeeType;
    }

    public void changeCommunityEarlyPaymentFeeAmount(CommunityEarlyPaymentFeeAmount earlyPaymentFeeAmount) {
        if (earlyPaymentFeeAmount != null) this.communityEarlyPaymentFeeAmount = earlyPaymentFeeAmount;
    }

    public void changePricingTiers(Set<CommunityPricingTier> pricingTiers) {
        if (pricingTiers != null) this.pricingTiers = pricingTiers;
    }

    public void updateAudit(UUID updatedBy) {
        this.audit = this.audit.update(updatedBy);
        if (this.pricingTiers != null) {
            this.pricingTiers.forEach(tier -> tier.updateAudit(updatedBy));
        }
    }

    public void deleteAudit(UUID deletedBy) {
        this.audit = this.audit.delete(deletedBy);
        if (this.pricingTiers != null) {
            this.pricingTiers.forEach(tier -> tier.deleteAudit(deletedBy));
        }
    }
}