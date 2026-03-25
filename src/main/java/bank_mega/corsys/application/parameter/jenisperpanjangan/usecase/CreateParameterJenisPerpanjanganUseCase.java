package bank_mega.corsys.application.parameter.jenisperpanjangan.usecase;

import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.application.parameter.jenisperpanjangan.command.CreateParameterJenisPerpanjanganCommand;
import bank_mega.corsys.application.parameter.jenisperpanjangan.dto.ParameterJenisPerpanjanganResponse;
import bank_mega.corsys.domain.exception.DomainRuleViolationException;
import bank_mega.corsys.domain.model.common.AuditTrail;
import bank_mega.corsys.domain.model.parameter.jenisperpanjangan.ParameterJenisPerpanjangan;
import bank_mega.corsys.domain.model.parameter.jenisperpanjangan.ParameterJenisPerpanjanganCode;
import bank_mega.corsys.domain.model.parameter.jenisperpanjangan.ParameterJenisPerpanjanganId;
import bank_mega.corsys.domain.model.parameter.jenisperpanjangan.ParameterJenisPerpanjanganValue;
import bank_mega.corsys.domain.model.user.User;
import bank_mega.corsys.domain.repository.ParameterJenisPerpanjanganRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
public class CreateParameterJenisPerpanjanganUseCase {

    private final ParameterJenisPerpanjanganRepository parameterJenisPerpanjanganRepository;

    @Transactional
    public ParameterJenisPerpanjanganResponse execute(CreateParameterJenisPerpanjanganCommand command, User authPrincipal) {
        // Validate code uniqueness
        parameterJenisPerpanjanganRepository.findFirstByCode(new ParameterJenisPerpanjanganCode(command.code()))
                .ifPresent(existing -> {
                    throw new DomainRuleViolationException("ParameterJenisPerpanjangan code already exists: " + command.code());
                });

        // Create new parameter
        ParameterJenisPerpanjangan newParameter = new ParameterJenisPerpanjangan(
                new ParameterJenisPerpanjanganId(command.code()),
                new ParameterJenisPerpanjanganCode(command.code()),
                new ParameterJenisPerpanjanganValue(command.value()),
                AuditTrail.create(authPrincipal.getId().value())
        );

        // Save to repository
        ParameterJenisPerpanjangan saved = parameterJenisPerpanjanganRepository.save(newParameter);

        // Convert to response DTO
        return ParameterJenisPerpanjanganResponse.builder()
                .code(saved.getCode().value())
                .value(saved.getValue().value())
                .build();
    }

}
