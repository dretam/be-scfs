package bank_mega.corsys.application.parameter.metodepembayaranbunga.usecase;

import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.application.parameter.metodepembayaranbunga.command.UpdateParameterMetodePembayaranBungaCommand;
import bank_mega.corsys.application.parameter.metodepembayaranbunga.dto.ParameterMetodePembayaranBungaResponse;
import bank_mega.corsys.domain.exception.DomainRuleViolationException;
import bank_mega.corsys.domain.exception.ParameterMetodePembayaranBungaNotFoundException;
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
public class UpdateParameterMetodePembayaranBungaUseCase {

    private final ParameterMetodePembayaranBungaRepository parameterMetodePembayaranBungaRepository;

    @Transactional
    public ParameterMetodePembayaranBungaResponse execute(UpdateParameterMetodePembayaranBungaCommand command, User authPrincipal) {
        // Find existing parameter
        ParameterMetodePembayaranBunga parameter = parameterMetodePembayaranBungaRepository.findFirstByIdAndAuditDeletedAtIsNull(
                        new ParameterMetodePembayaranBungaId(command.code()))
                .orElseThrow(() -> new ParameterMetodePembayaranBungaNotFoundException(new ParameterMetodePembayaranBungaId(command.code())));

        // Update fields
        parameter.changeValue(new ParameterMetodePembayaranBungaValue(command.value()));

        // Update audit
        parameter.updateAudit(authPrincipal.getId().value());

        // Save to repository
        ParameterMetodePembayaranBunga saved = parameterMetodePembayaranBungaRepository.save(parameter);

        // Convert to response DTO
        return ParameterMetodePembayaranBungaResponse.builder()
                .code(saved.getCode().value())
                .value(saved.getValue().value())
                .build();
    }

}
