package bank_mega.corsys.application.community.usecase;

import bank_mega.corsys.application.assembler.CommunityAssembler;
import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.application.community.command.UpdateCommunityCommand;
import bank_mega.corsys.application.community.dto.CommunityResponse;
import bank_mega.corsys.domain.exception.CommunityNotFoundException;
import bank_mega.corsys.domain.model.common.AuditTrail;
import bank_mega.corsys.domain.model.community.*;
import bank_mega.corsys.domain.model.user.User;
import bank_mega.corsys.domain.repository.CommunityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@UseCase
@RequiredArgsConstructor
public class UpdateCommunityUseCase {

    private final CommunityRepository communityRepository;

    @Transactional
    public CommunityResponse execute(UpdateCommunityCommand command, User authPrincipal) {

        Community community = communityRepository.findFirstByIdAndAuditDeletedAtIsNull(new CommunityId(command.communityId()))
                .orElseThrow(() -> new CommunityNotFoundException(new CommunityId(command.communityId())));

        command.name().ifPresent(name -> community.changeCommunityName(new CommunityName(name)));
        command.description().ifPresent(desc -> community.changeCommunityDescription(new CommunityDescription(desc)));
        command.productType().ifPresent(community::changeProductType);
        command.recourse().ifPresent(community::changeRecourse);
        command.holidayTreatment().ifPresent(community::changeHolidayTreatment);
        command.transactionFeeType().ifPresent(community::changeTransactionFeeType);
        command.interestType().ifPresent(community::changeInterestType);
        command.interestBasis().ifPresent(community::changeInterestBasis);
        command.isActive().ifPresent(val -> community.changeCommunityIsActive(new CommunityIsActive(val)));
        command.isInterestReserve().ifPresent(val -> community.changeCommunityIsInterestReserve(new CommunityIsInterestReserve(val)));
        command.isFundReserve().ifPresent(val -> community.changeCommunityIsFundReserve(new CommunityIsFundReserve(val)));
        command.isSharingIncome().ifPresent(val -> community.changeCommunityIsSharingIncome(new CommunityIsSharingIncome(val)));
        command.isExtendFinancing().ifPresent(val -> community.changeCommunityIsExtendFinancing(new CommunityIsExtendFinancing(val)));
        command.transactionFee().ifPresent(val -> community.changeCommunityTransactionFee(new CommunityTransactionFee(val)));
        command.minFinancing().ifPresent(val -> community.changeCommunityMinFinancing(new CommunityMinFinancing(val)));
        command.maxFinancing().ifPresent(val -> community.changeCommunityMaxFinancing(new CommunityMaxFinancing(val)));
        command.minTenor().ifPresent(val -> community.changeCommunityMinTenor(new CommunityMinTenor(val)));
        command.maxTenor().ifPresent(val -> community.changeCommunityMaxTenor(new CommunityMaxTenor(val)));
        command.gracePeriod().ifPresent(val -> community.changeCommunityGracePeriod(new CommunityGracePeriod(val)));
        command.penaltyRate().ifPresent(val -> community.changeCommunityPenaltyRate(new CommunityPenaltyRate(val)));
        command.earlyPaymentFeeType().ifPresent(community::changeEarlyPaymentFeeType);
        command.earlyPaymentFeeAmount().ifPresent(val -> community.changeCommunityEarlyPaymentFeeAmount(new CommunityEarlyPaymentFeeAmount(val)));

        command.pricingTiers().ifPresent(ptCommands -> {
            Set<CommunityPricingTier> updatedTiers = ptCommands.stream()
                    .map(ptCommand -> {
                        if (ptCommand.id().isPresent()) {
                            CommunityPricingTier existingTier = community.getPricingTiers().stream()
                                    .filter(tier -> tier.getId().value().equals(ptCommand.id().get()))
                                    .findFirst()
                                    .orElseThrow(() -> new IllegalArgumentException("Pricing tier not found with id: " + ptCommand.id().get()));

                            ptCommand.logic().ifPresent(existingTier::changeLogic);
                            ptCommand.nominal().ifPresent(val -> existingTier.changeNominal(new PricingTierNominal(val)));
                            ptCommand.quantity().ifPresent(val -> existingTier.changeQuantity(new PricingTierQuantity(val)));
                            existingTier.updateAudit(authPrincipal.getId().value());
                            return existingTier;
                        } else {
                            PricingTierLogic logic = ptCommand.logic()
                                    .orElseThrow(() -> new IllegalArgumentException("Pricing tier logic is required when creating new tier"));
                            BigDecimal nominal = ptCommand.nominal()
                                    .orElseThrow(() -> new IllegalArgumentException("Pricing tier nominal is required when creating new tier"));
                            Integer quantity = ptCommand.quantity()
                                    .orElseThrow(() -> new IllegalArgumentException("Pricing tier quantity is required when creating new tier"));

                            return new CommunityPricingTier(
                                    null,
                                    logic,
                                    new PricingTierNominal(nominal),
                                    new PricingTierQuantity(quantity),
                                    AuditTrail.create(authPrincipal.getId().value())
                            );
                        }
                    })
                    .collect(Collectors.toSet());

            community.changePricingTiers(updatedTiers);
        });

        community.updateAudit(authPrincipal.getId().value());

        Community saved = communityRepository.save(community);

        return CommunityAssembler.toResponse(saved);
    }
}