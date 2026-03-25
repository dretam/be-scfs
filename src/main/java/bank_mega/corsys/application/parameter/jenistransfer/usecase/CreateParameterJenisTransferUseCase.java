package bank_mega.corsys.application.parameter.jenistransfer.usecase;

import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.application.parameter.jenistransfer.command.CreateParameterJenisTransferCommand;
import bank_mega.corsys.application.parameter.jenistransfer.dto.ParameterJenisTransferResponse;
import bank_mega.corsys.domain.exception.DomainRuleViolationException;
import bank_mega.corsys.domain.model.common.AuditTrail;
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
public class CreateParameterJenisTransferUseCase {

    private final ParameterJenisTransferRepository parameterJenisTransferRepository;

    @Transactional
    public ParameterJenisTransferResponse execute(CreateParameterJenisTransferCommand command, User authPrincipal) {
        // Validate code uniqueness
        parameterJenisTransferRepository.findFirstByCode(new ParameterJenisTransferCode(command.code()))
                .ifPresent(existing -> {
                    throw new DomainRuleViolationException("ParameterJenisTransfer code already exists: " + command.code());
                });

        // Create new parameter
        ParameterJenisTransfer newParameter = new ParameterJenisTransfer(
                new ParameterJenisTransferId(command.code()),
                new ParameterJenisTransferCode(command.code()),
                new ParameterJenisTransferValue(command.value()),
                AuditTrail.create(authPrincipal.getId().value())
        );

        // Save to repository
        ParameterJenisTransfer saved = parameterJenisTransferRepository.save(newParameter);

        // Convert to response DTO
        return ParameterJenisTransferResponse.builder()
                .code(saved.getCode().value())
                .value(saved.getValue().value())
                .build();
    }

}
