package bank_mega.corsys.application.parameter.jenisperpanjangan.usecase;

import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.application.parameter.jenisperpanjangan.command.UpdateParameterJenisPerpanjanganCommand;
import bank_mega.corsys.application.parameter.jenisperpanjangan.dto.ParameterJenisPerpanjanganResponse;
import bank_mega.corsys.domain.exception.DomainRuleViolationException;
import bank_mega.corsys.domain.exception.ParameterJenisPerpanjanganNotFoundException;
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
public class UpdateParameterJenisPerpanjanganUseCase {

    private final ParameterJenisPerpanjanganRepository parameterJenisPerpanjanganRepository;

    @Transactional
    public ParameterJenisPerpanjanganResponse execute(UpdateParameterJenisPerpanjanganCommand command, User authPrincipal) {
        // Find existing parameter
        ParameterJenisPerpanjangan parameter = parameterJenisPerpanjanganRepository.findFirstByIdAndAuditDeletedAtIsNull(
                        new ParameterJenisPerpanjanganId(command.code()))
                .orElseThrow(() -> new ParameterJenisPerpanjanganNotFoundException(new ParameterJenisPerpanjanganId(command.code())));

        // Update fields
        parameter.changeValue(new ParameterJenisPerpanjanganValue(command.value()));

        // Update audit
        parameter.updateAudit(authPrincipal.getId().value());

        // Save to repository
        ParameterJenisPerpanjangan saved = parameterJenisPerpanjanganRepository.save(parameter);

        // Convert to response DTO
        return ParameterJenisPerpanjanganResponse.builder()
                .code(saved.getCode().value())
                .value(saved.getValue().value())
                .build();
    }

}
