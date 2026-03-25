package bank_mega.corsys.application.parameter.jenistransfer.usecase;

import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.application.parameter.jenistransfer.command.UpdateParameterJenisTransferCommand;
import bank_mega.corsys.application.parameter.jenistransfer.dto.ParameterJenisTransferResponse;
import bank_mega.corsys.domain.exception.DomainRuleViolationException;
import bank_mega.corsys.domain.exception.ParameterJenisTransferNotFoundException;
import bank_mega.corsys.domain.model.parameter.jenistransfer.ParameterJenisTransfer;
import bank_mega.corsys.domain.model.parameter.jenistransfer.ParameterJenisTransferCode;
import bank_mega.corsys.domain.model.parameter.jenistransfer.ParameterJenisTransferId;
import bank_mega.corsys.domain.model.parameter.jenistransfer.ParameterJenisTransferValue;
import bank_mega.corsys.domain.model.user.User;
import bank_mega.corsys.domain.repository.ParameterJenisTransferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
public class UpdateParameterJenisTransferUseCase {

    private final ParameterJenisTransferRepository parameterJenisTransferRepository;

    @Transactional
    public ParameterJenisTransferResponse execute(UpdateParameterJenisTransferCommand command, User authPrincipal) {
        // Find existing parameter
        ParameterJenisTransfer parameter = parameterJenisTransferRepository.findFirstByIdAndAuditDeletedAtIsNull(
                        new ParameterJenisTransferId(command.code()))
                .orElseThrow(() -> new ParameterJenisTransferNotFoundException(new ParameterJenisTransferId(command.code())));

        // Update fields
        parameter.changeValue(new ParameterJenisTransferValue(command.value()));

        // Update audit
        parameter.updateAudit(authPrincipal.getId().value());

        // Save to repository
        ParameterJenisTransfer saved = parameterJenisTransferRepository.save(parameter);

        // Convert to response DTO
        return ParameterJenisTransferResponse.builder()
                .code(saved.getCode().value())
                .value(saved.getValue().value())
                .build();
    }

}
