package bank_mega.corsys.application.parameter.metodepembayaranpokok.usecase;

import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.application.parameter.metodepembayaranpokok.command.UpdateParameterMetodePembayaranPokokCommand;
import bank_mega.corsys.application.parameter.metodepembayaranpokok.dto.ParameterMetodePembayaranPokokResponse;
import bank_mega.corsys.domain.exception.DomainRuleViolationException;
import bank_mega.corsys.domain.exception.ParameterMetodePembayaranPokokNotFoundException;
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
public class UpdateParameterMetodePembayaranPokokUseCase {

    private final ParameterMetodePembayaranPokokRepository parameterMetodePembayaranPokokRepository;

    @Transactional
    public ParameterMetodePembayaranPokokResponse execute(UpdateParameterMetodePembayaranPokokCommand command, User authPrincipal) {
        // Find existing parameter
        ParameterMetodePembayaranPokok parameter = parameterMetodePembayaranPokokRepository.findFirstByIdAndAuditDeletedAtIsNull(
                        new ParameterMetodePembayaranPokokId(command.code()))
                .orElseThrow(() -> new ParameterMetodePembayaranPokokNotFoundException(new ParameterMetodePembayaranPokokId(command.code())));

        // Update fields
        parameter.changeValue(new ParameterMetodePembayaranPokokValue(command.value()));

        // Update audit
        parameter.updateAudit(authPrincipal.getId().value());

        // Save to repository
        ParameterMetodePembayaranPokok saved = parameterMetodePembayaranPokokRepository.save(parameter);

        // Convert to response DTO
        return ParameterMetodePembayaranPokokResponse.builder()
                .code(saved.getCode().value())
                .value(saved.getValue().value())
                .build();
    }

}
