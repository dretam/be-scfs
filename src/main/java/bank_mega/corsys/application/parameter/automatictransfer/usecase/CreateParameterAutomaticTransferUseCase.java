package bank_mega.corsys.application.parameter.automatictransfer.usecase;

import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.application.parameter.automatictransfer.command.CreateParameterAutomaticTransferCommand;
import bank_mega.corsys.application.parameter.automatictransfer.dto.ParameterAutomaticTransferResponse;
import bank_mega.corsys.domain.exception.DomainRuleViolationException;
import bank_mega.corsys.domain.model.common.AuditTrail;
import bank_mega.corsys.domain.model.parameter.automatictransfer.ParameterAutomaticTransfer;
import bank_mega.corsys.domain.model.parameter.automatictransfer.ParameterAutomaticTransferCode;
import bank_mega.corsys.domain.model.parameter.automatictransfer.ParameterAutomaticTransferId;
import bank_mega.corsys.domain.model.parameter.automatictransfer.ParameterAutomaticTransferValue;
import bank_mega.corsys.domain.model.user.User;
import bank_mega.corsys.domain.repository.ParameterAutomaticTransferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
public class CreateParameterAutomaticTransferUseCase {

    private final ParameterAutomaticTransferRepository parameterAutomaticTransferRepository;

    @Transactional
    public ParameterAutomaticTransferResponse execute(CreateParameterAutomaticTransferCommand command, User authPrincipal) {
        // Validate code uniqueness
        parameterAutomaticTransferRepository.findFirstByCode(new ParameterAutomaticTransferCode(command.code()))
                .ifPresent(existing -> {
                    throw new DomainRuleViolationException("ParameterAutomaticTransfer code already exists: " + command.code());
                });

        // Create new parameter
        ParameterAutomaticTransfer newParameter = new ParameterAutomaticTransfer(
                new ParameterAutomaticTransferId(command.code()),
                new ParameterAutomaticTransferCode(command.code()),
                new ParameterAutomaticTransferValue(command.value()),
                AuditTrail.create(authPrincipal.getId().value())
        );

        // Save to repository
        ParameterAutomaticTransfer saved = parameterAutomaticTransferRepository.save(newParameter);

        // Convert to response DTO
        return ParameterAutomaticTransferResponse.builder()
                .code(saved.getCode().value())
                .value(saved.getValue().value())
                .build();
    }

}
