package bank_mega.corsys.application.parameter.metodepembayaranpokok.usecase;

import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.application.parameter.metodepembayaranpokok.command.CreateParameterMetodePembayaranPokokCommand;
import bank_mega.corsys.application.parameter.metodepembayaranpokok.dto.ParameterMetodePembayaranPokokResponse;
import bank_mega.corsys.domain.exception.DomainRuleViolationException;
import bank_mega.corsys.domain.model.common.AuditTrail;
import bank_mega.corsys.domain.model.parameter.metodepembayaranpokok.ParameterMetodePembayaranPokok;
import bank_mega.corsys.domain.model.parameter.metodepembayaranpokok.ParameterMetodePembayaranPokokCode;
import bank_mega.corsys.domain.model.parameter.metodepembayaranpokok.ParameterMetodePembayaranPokokId;
import bank_mega.corsys.domain.model.parameter.metodepembayaranpokok.ParameterMetodePembayaranPokokValue;
import bank_mega.corsys.domain.model.user.User;
import bank_mega.corsys.domain.repository.ParameterMetodePembayaranPokokRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
public class CreateParameterMetodePembayaranPokokUseCase {

    private final ParameterMetodePembayaranPokokRepository parameterMetodePembayaranPokokRepository;

    @Transactional
    public ParameterMetodePembayaranPokokResponse execute(CreateParameterMetodePembayaranPokokCommand command, User authPrincipal) {
        // Validate code uniqueness
        parameterMetodePembayaranPokokRepository.findFirstByCode(new ParameterMetodePembayaranPokokCode(command.code()))
                .ifPresent(existing -> {
                    throw new DomainRuleViolationException("ParameterMetodePembayaranPokok code already exists: " + command.code());
                });

        // Create new parameter
        ParameterMetodePembayaranPokok newParameter = new ParameterMetodePembayaranPokok(
                new ParameterMetodePembayaranPokokId(command.code()),
                new ParameterMetodePembayaranPokokCode(command.code()),
                new ParameterMetodePembayaranPokokValue(command.value()),
                AuditTrail.create(authPrincipal.getId().value())
        );

        // Save to repository
        ParameterMetodePembayaranPokok saved = parameterMetodePembayaranPokokRepository.save(newParameter);

        // Convert to response DTO
        return ParameterMetodePembayaranPokokResponse.builder()
                .code(saved.getCode().value())
                .value(saved.getValue().value())
                .build();
    }

}
