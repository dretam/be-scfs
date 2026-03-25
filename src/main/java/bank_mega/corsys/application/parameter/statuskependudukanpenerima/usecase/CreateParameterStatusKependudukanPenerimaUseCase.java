package bank_mega.corsys.application.parameter.statuskependudukanpenerima.usecase;

import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.application.parameter.statuskependudukanpenerima.command.CreateParameterStatusKependudukanPenerimaCommand;
import bank_mega.corsys.application.parameter.statuskependudukanpenerima.dto.ParameterStatusKependudukanPenerimaResponse;
import bank_mega.corsys.domain.exception.DomainRuleViolationException;
import bank_mega.corsys.domain.model.common.AuditTrail;
import bank_mega.corsys.domain.model.parameter.statuskependudukanpenerima.ParameterStatusKependudukanPenerima;
import bank_mega.corsys.domain.model.parameter.statuskependudukanpenerima.ParameterStatusKependudukanPenerimaCode;
import bank_mega.corsys.domain.model.parameter.statuskependudukanpenerima.ParameterStatusKependudukanPenerimaId;
import bank_mega.corsys.domain.model.parameter.statuskependudukanpenerima.ParameterStatusKependudukanPenerimaValue;
import bank_mega.corsys.domain.model.user.User;
import bank_mega.corsys.domain.repository.ParameterStatusKependudukanPenerimaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
public class CreateParameterStatusKependudukanPenerimaUseCase {

    private final ParameterStatusKependudukanPenerimaRepository parameterStatusKependudukanPenerimaRepository;

    @Transactional
    public ParameterStatusKependudukanPenerimaResponse execute(CreateParameterStatusKependudukanPenerimaCommand command, User authPrincipal) {
        // Validate code uniqueness
        parameterStatusKependudukanPenerimaRepository.findFirstByCode(new ParameterStatusKependudukanPenerimaCode(command.code()))
                .ifPresent(existing -> {
                    throw new DomainRuleViolationException("ParameterStatusKependudukanPenerima code already exists: " + command.code());
                });

        // Create new parameter
        ParameterStatusKependudukanPenerima newParameter = new ParameterStatusKependudukanPenerima(
                new ParameterStatusKependudukanPenerimaId(command.code()),
                new ParameterStatusKependudukanPenerimaCode(command.code()),
                new ParameterStatusKependudukanPenerimaValue(command.value()),
                AuditTrail.create(authPrincipal.getId().value())
        );

        // Save to repository
        ParameterStatusKependudukanPenerima saved = parameterStatusKependudukanPenerimaRepository.save(newParameter);

        // Convert to response DTO
        return ParameterStatusKependudukanPenerimaResponse.builder()
                .code(saved.getCode().value())
                .value(saved.getValue().value())
                .build();
    }

}
