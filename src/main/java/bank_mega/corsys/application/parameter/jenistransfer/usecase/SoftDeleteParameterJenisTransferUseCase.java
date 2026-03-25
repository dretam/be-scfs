package bank_mega.corsys.application.parameter.jenistransfer.usecase;

import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.application.parameter.jenistransfer.command.SoftDeleteParameterJenisTransferCommand;
import bank_mega.corsys.application.parameter.jenistransfer.dto.ParameterJenisTransferResponse;
import bank_mega.corsys.domain.exception.ParameterJenisTransferNotFoundException;
import bank_mega.corsys.domain.model.parameter.jenistransfer.ParameterJenisTransfer;
import bank_mega.corsys.domain.model.parameter.jenistransfer.ParameterJenisTransferId;
import bank_mega.corsys.domain.model.user.User;
import bank_mega.corsys.domain.repository.ParameterJenisTransferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
public class SoftDeleteParameterJenisTransferUseCase {

    private final ParameterJenisTransferRepository parameterJenisTransferRepository;

    @Transactional
    public ParameterJenisTransferResponse execute(SoftDeleteParameterJenisTransferCommand command, User authPrincipal) {
        ParameterJenisTransfer parameter = parameterJenisTransferRepository.findFirstByIdAndAuditDeletedAtIsNull(
                        new ParameterJenisTransferId(command.code()))
                .orElseThrow(() -> new ParameterJenisTransferNotFoundException(new ParameterJenisTransferId(command.code())));

        parameter.deleteAudit(authPrincipal.getId().value());

        ParameterJenisTransfer deleted = parameterJenisTransferRepository.save(parameter);

        return ParameterJenisTransferResponse.builder()
                .code(deleted.getCode().value())
                .value(deleted.getValue().value())
                .build();
    }

}
