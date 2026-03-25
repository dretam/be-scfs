package bank_mega.corsys.application.parameter.sumberdana.usecase;

import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.application.parameter.sumberdana.command.UpdateParameterSumberDanaCommand;
import bank_mega.corsys.application.parameter.sumberdana.dto.ParameterSumberDanaResponse;
import bank_mega.corsys.domain.exception.DomainRuleViolationException;
import bank_mega.corsys.domain.exception.ParameterSumberDanaNotFoundException;
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
public class UpdateParameterSumberDanaUseCase {

    private final ParameterSumberDanaRepository parameterSumberDanaRepository;

    @Transactional
    public ParameterSumberDanaResponse execute(UpdateParameterSumberDanaCommand command, User authPrincipal) {
        // Find existing parameter
        ParameterSumberDana parameter = parameterSumberDanaRepository.findFirstByIdAndAuditDeletedAtIsNull(
                        new ParameterSumberDanaId(command.code()))
                .orElseThrow(() -> new ParameterSumberDanaNotFoundException(new ParameterSumberDanaId(command.code())));

        // Update fields
        parameter.changeValue(new ParameterSumberDanaValue(command.value()));

        // Update audit
        parameter.updateAudit(authPrincipal.getId().value());

        // Save to repository
        ParameterSumberDana saved = parameterSumberDanaRepository.save(parameter);

        // Convert to response DTO
        return ParameterSumberDanaResponse.builder()
                .code(saved.getCode().value())
                .value(saved.getValue().value())
                .build();
    }

}
