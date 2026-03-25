package bank_mega.corsys.application.parameter.statuskependudukanpenerima.usecase;

import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.application.parameter.statuskependudukanpenerima.command.UpdateParameterStatusKependudukanPenerimaCommand;
import bank_mega.corsys.application.parameter.statuskependudukanpenerima.dto.ParameterStatusKependudukanPenerimaResponse;
import bank_mega.corsys.domain.exception.DomainRuleViolationException;
import bank_mega.corsys.domain.exception.ParameterStatusKependudukanPenerimaNotFoundException;
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
public class UpdateParameterStatusKependudukanPenerimaUseCase {

    private final ParameterStatusKependudukanPenerimaRepository parameterStatusKependudukanPenerimaRepository;

    @Transactional
    public ParameterStatusKependudukanPenerimaResponse execute(UpdateParameterStatusKependudukanPenerimaCommand command, User authPrincipal) {
        // Find existing parameter
        ParameterStatusKependudukanPenerima parameter = parameterStatusKependudukanPenerimaRepository.findFirstByIdAndAuditDeletedAtIsNull(
                        new ParameterStatusKependudukanPenerimaId(command.code()))
                .orElseThrow(() -> new ParameterStatusKependudukanPenerimaNotFoundException(new ParameterStatusKependudukanPenerimaId(command.code())));

        // Update fields
        parameter.changeValue(new ParameterStatusKependudukanPenerimaValue(command.value()));

        // Update audit
        parameter.updateAudit(authPrincipal.getId().value());

        // Save to repository
        ParameterStatusKependudukanPenerima saved = parameterStatusKependudukanPenerimaRepository.save(parameter);

        // Convert to response DTO
        return ParameterStatusKependudukanPenerimaResponse.builder()
                .code(saved.getCode().value())
                .value(saved.getValue().value())
                .build();
    }

}
