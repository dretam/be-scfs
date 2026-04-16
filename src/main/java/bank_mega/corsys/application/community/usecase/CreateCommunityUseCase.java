package bank_mega.corsys.application.community.usecase;

import bank_mega.corsys.application.assembler.CommunityAssembler;
import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.application.community.command.CreateCommunityCommand;
import bank_mega.corsys.application.community.dto.CommunityResponse;
import bank_mega.corsys.domain.model.common.AuditTrail;
import bank_mega.corsys.domain.model.community.*;
import bank_mega.corsys.domain.model.user.User;
import bank_mega.corsys.domain.repository.CommunityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@UseCase
@RequiredArgsConstructor
public class CreateCommunityUseCase {

    private final CommunityRepository communityRepository;

    @Transactional
    public CommunityResponse execute(CreateCommunityCommand command, User authPrincipal) {

        Set<CommunityPricingTier> pricingTiers = new HashSet<>();
        if (command.pricingTiers() != null && !command.pricingTiers().isEmpty()) {
            pricingTiers = command.pricingTiers().stream()
                    .map(ptCommand -> new CommunityPricingTier(
                            new PricingTierId(UUID.randomUUID()),
                            ptCommand.logic(),
                            new PricingTierNominal(ptCommand.nominal()),
                            new PricingTierQuantity(ptCommand.quantity()),
                            AuditTrail.create(authPrincipal.getId().value())
                    ))
                    .collect(Collectors.toSet());
        }

        Community newCommunity = new Community(
                new CommunityId(UUID.randomUUID()),
                new CommunityName(command.name()),
                new CommunityDescription(command.description()),
                command.productType(),
                command.recourse(),
                command.holidayTreatment(),
                command.transactionFeeType(),
                command.interestType(),
                command.interestBasis(),
                new CommunityIsActive(command.isActive()),
                new CommunityIsInterestReserve(command.isInterestReserve()),
                new CommunityIsFundReserve(command.isFundReserve()),
                new CommunityIsSharingIncome(command.isSharingIncome()),
                new CommunityIsExtendFinancing(command.isExtendFinancing()),
                new CommunityTransactionFee(command.transactionFee()),
                new CommunityMinFinancing(command.minFinancing()),
                new CommunityMaxFinancing(command.maxFinancing()),
                new CommunityMinTenor(command.minTenor()),
                new CommunityMaxTenor(command.maxTenor()),
                new CommunityGracePeriod(command.gracePeriod()),
                new CommunityPenaltyRate(command.penaltyRate()),
                command.earlyPaymentFeeType(),
                new CommunityEarlyPaymentFeeAmount(command.earlyPaymentFeeAmount()),
                pricingTiers,
                AuditTrail.create(authPrincipal.getId().value())
        );

        Community saved = communityRepository.save(newCommunity);

        return CommunityAssembler.toResponse(saved);
    }

}
