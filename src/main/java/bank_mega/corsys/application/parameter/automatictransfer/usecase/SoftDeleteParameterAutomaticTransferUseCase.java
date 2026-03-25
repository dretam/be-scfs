package bank_mega.corsys.application.parameter.automatictransfer.usecase;

import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.application.parameter.automatictransfer.command.SoftDeleteParameterAutomaticTransferCommand;
import bank_mega.corsys.application.parameter.automatictransfer.dto.ParameterAutomaticTransferResponse;
import bank_mega.corsys.domain.exception.ParameterAutomaticTransferNotFoundException;
import bank_mega.corsys.domain.model.parameter.automatictransfer.ParameterAutomaticTransfer;
import bank_mega.corsys.domain.model.parameter.automatictransfer.ParameterAutomaticTransferId;
import bank_mega.corsys.domain.model.user.User;
import bank_mega.corsys.domain.repository.ParameterAutomaticTransferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
public class SoftDeleteParameterAutomaticTransferUseCase {

    private final ParameterAutomaticTransferRepository parameterAutomaticTransferRepository;

    @Transactional
    public ParameterAutomaticTransferResponse execute(SoftDeleteParameterAutomaticTransferCommand command, User authPrincipal) {
        ParameterAutomaticTransfer parameter = parameterAutomaticTransferRepository.findFirstByIdAndAuditDeletedAtIsNull(
                        new ParameterAutomaticTransferId(command.code()))
                .orElseThrow(() -> new ParameterAutomaticTransferNotFoundException(new ParameterAutomaticTransferId(command.code())));

        parameter.deleteAudit(authPrincipal.getId().value());

        ParameterAutomaticTransfer deleted = parameterAutomaticTransferRepository.save(parameter);

        return ParameterAutomaticTransferResponse.builder()
                .code(deleted.getCode().value())
                .value(deleted.getValue().value())
                .build();
    }

}
