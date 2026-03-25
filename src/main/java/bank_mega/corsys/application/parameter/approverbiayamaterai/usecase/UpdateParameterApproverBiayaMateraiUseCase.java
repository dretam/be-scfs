package bank_mega.corsys.application.parameter.approverbiayamaterai.usecase;

import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.application.parameter.approverbiayamaterai.command.UpdateParameterApproverBiayaMateraiCommand;
import bank_mega.corsys.application.parameter.approverbiayamaterai.dto.ParameterApproverBiayaMateraiResponse;
import bank_mega.corsys.domain.exception.DomainRuleViolationException;
import bank_mega.corsys.domain.exception.ParameterApproverBiayaMateraiNotFoundException;
import bank_mega.corsys.domain.model.parameter.approverbiayamaterai.ParameterApproverBiayaMaterai;
import bank_mega.corsys.domain.model.parameter.approverbiayamaterai.ParameterApproverBiayaMateraiCode;
import bank_mega.corsys.domain.model.parameter.approverbiayamaterai.ParameterApproverBiayaMateraiId;
import bank_mega.corsys.domain.model.parameter.approverbiayamaterai.ParameterApproverBiayaMateraiValue;
import bank_mega.corsys.domain.model.user.User;
import bank_mega.corsys.domain.repository.ParameterApproverBiayaMateraiRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
public class UpdateParameterApproverBiayaMateraiUseCase {

    private final ParameterApproverBiayaMateraiRepository parameterApproverBiayaMateraiRepository;

    @Transactional
    public ParameterApproverBiayaMateraiResponse execute(UpdateParameterApproverBiayaMateraiCommand command, User authPrincipal) {
        // Find existing parameter
        ParameterApproverBiayaMaterai parameter = parameterApproverBiayaMateraiRepository.findFirstByIdAndAuditDeletedAtIsNull(
                        new ParameterApproverBiayaMateraiId(command.code()))
                .orElseThrow(() -> new ParameterApproverBiayaMateraiNotFoundException(new ParameterApproverBiayaMateraiId(command.code())));

        // Update fields
        parameter.changeValue(new ParameterApproverBiayaMateraiValue(command.value()));

        // Update audit
        parameter.updateAudit(authPrincipal.getId().value());

        // Save to repository
        ParameterApproverBiayaMaterai saved = parameterApproverBiayaMateraiRepository.save(parameter);

        // Convert to response DTO
        return ParameterApproverBiayaMateraiResponse.builder()
                .code(saved.getCode().value())
                .value(saved.getValue().value())
                .build();
    }

}
