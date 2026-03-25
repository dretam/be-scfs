package bank_mega.corsys.application.parameter.automatictransfer.usecase;

import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.application.parameter.automatictransfer.command.UpdateParameterAutomaticTransferCommand;
import bank_mega.corsys.application.parameter.automatictransfer.dto.ParameterAutomaticTransferResponse;
import bank_mega.corsys.domain.exception.DomainRuleViolationException;
import bank_mega.corsys.domain.exception.ParameterAutomaticTransferNotFoundException;
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
public class UpdateParameterAutomaticTransferUseCase {

    private final ParameterAutomaticTransferRepository parameterAutomaticTransferRepository;

    @Transactional
    public ParameterAutomaticTransferResponse execute(UpdateParameterAutomaticTransferCommand command, User authPrincipal) {
        // Find existing parameter
        ParameterAutomaticTransfer parameter = parameterAutomaticTransferRepository.findFirstByIdAndAuditDeletedAtIsNull(
                        new ParameterAutomaticTransferId(command.code()))
                .orElseThrow(() -> new ParameterAutomaticTransferNotFoundException(new ParameterAutomaticTransferId(command.code())));

        // Update fields
        parameter.changeValue(new ParameterAutomaticTransferValue(command.value()));

        // Update audit
        parameter.updateAudit(authPrincipal.getId().value());

        // Save to repository
        ParameterAutomaticTransfer saved = parameterAutomaticTransferRepository.save(parameter);

        // Convert to response DTO
        return ParameterAutomaticTransferResponse.builder()
                .code(saved.getCode().value())
                .value(saved.getValue().value())
                .build();
    }

}
