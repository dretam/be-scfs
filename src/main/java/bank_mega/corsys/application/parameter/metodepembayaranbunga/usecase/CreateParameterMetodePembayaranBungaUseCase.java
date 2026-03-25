package bank_mega.corsys.application.parameter.metodepembayaranbunga.usecase;

import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.application.parameter.metodepembayaranbunga.command.CreateParameterMetodePembayaranBungaCommand;
import bank_mega.corsys.application.parameter.metodepembayaranbunga.dto.ParameterMetodePembayaranBungaResponse;
import bank_mega.corsys.domain.exception.DomainRuleViolationException;
import bank_mega.corsys.domain.model.common.AuditTrail;
import bank_mega.corsys.domain.model.parameter.metodepembayaranbunga.ParameterMetodePembayaranBunga;
import bank_mega.corsys.domain.model.parameter.metodepembayaranbunga.ParameterMetodePembayaranBungaCode;
import bank_mega.corsys.domain.model.parameter.metodepembayaranbunga.ParameterMetodePembayaranBungaId;
import bank_mega.corsys.domain.model.parameter.metodepembayaranbunga.ParameterMetodePembayaranBungaValue;
import bank_mega.corsys.domain.model.user.User;
import bank_mega.corsys.domain.repository.ParameterMetodePembayaranBungaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
public class CreateParameterMetodePembayaranBungaUseCase {

    private final ParameterMetodePembayaranBungaRepository parameterMetodePembayaranBungaRepository;

    @Transactional
    public ParameterMetodePembayaranBungaResponse execute(CreateParameterMetodePembayaranBungaCommand command, User authPrincipal) {
        // Validate code uniqueness
        parameterMetodePembayaranBungaRepository.findFirstByCode(new ParameterMetodePembayaranBungaCode(command.code()))
                .ifPresent(existing -> {
                    throw new DomainRuleViolationException("ParameterMetodePembayaranBunga code already exists: " + command.code());
                });

        // Create new parameter
        ParameterMetodePembayaranBunga newParameter = new ParameterMetodePembayaranBunga(
                new ParameterMetodePembayaranBungaId(command.code()),
                new ParameterMetodePembayaranBungaCode(command.code()),
                new ParameterMetodePembayaranBungaValue(command.value()),
                AuditTrail.create(authPrincipal.getId().value())
        );

        // Save to repository
        ParameterMetodePembayaranBunga saved = parameterMetodePembayaranBungaRepository.save(newParameter);

        // Convert to response DTO
        return ParameterMetodePembayaranBungaResponse.builder()
                .code(saved.getCode().value())
                .value(saved.getValue().value())
                .build();
    }

}
