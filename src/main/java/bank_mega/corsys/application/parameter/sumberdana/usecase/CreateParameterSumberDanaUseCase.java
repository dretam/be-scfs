package bank_mega.corsys.application.parameter.sumberdana.usecase;

import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.application.parameter.sumberdana.command.CreateParameterSumberDanaCommand;
import bank_mega.corsys.application.parameter.sumberdana.dto.ParameterSumberDanaResponse;
import bank_mega.corsys.domain.exception.DomainRuleViolationException;
import bank_mega.corsys.domain.model.common.AuditTrail;
import bank_mega.corsys.domain.model.parameter.sumberdana.ParameterSumberDana;
import bank_mega.corsys.domain.model.parameter.sumberdana.ParameterSumberDanaCode;
import bank_mega.corsys.domain.model.parameter.sumberdana.ParameterSumberDanaId;
import bank_mega.corsys.domain.model.parameter.sumberdana.ParameterSumberDanaValue;
import bank_mega.corsys.domain.model.user.User;
import bank_mega.corsys.domain.repository.ParameterSumberDanaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
public class CreateParameterSumberDanaUseCase {

    private final ParameterSumberDanaRepository parameterSumberDanaRepository;

    @Transactional
    public ParameterSumberDanaResponse execute(CreateParameterSumberDanaCommand command, User authPrincipal) {
        // Validate code uniqueness
        parameterSumberDanaRepository.findFirstByCode(new ParameterSumberDanaCode(command.code()))
                .ifPresent(existing -> {
                    throw new DomainRuleViolationException("ParameterSumberDana code already exists: " + command.code());
                });

        // Create new parameter
        ParameterSumberDana newParameter = new ParameterSumberDana(
                new ParameterSumberDanaId(command.code()),
                new ParameterSumberDanaCode(command.code()),
                new ParameterSumberDanaValue(command.value()),
                AuditTrail.create(authPrincipal.getId().value())
        );

        // Save to repository
        ParameterSumberDana saved = parameterSumberDanaRepository.save(newParameter);

        // Convert to response DTO
        return ParameterSumberDanaResponse.builder()
                .code(saved.getCode().value())
                .value(saved.getValue().value())
                .build();
    }

}
