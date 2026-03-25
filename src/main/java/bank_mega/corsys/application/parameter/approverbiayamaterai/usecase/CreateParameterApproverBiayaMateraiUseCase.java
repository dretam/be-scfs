package bank_mega.corsys.application.parameter.approverbiayamaterai.usecase;

import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.application.parameter.approverbiayamaterai.command.CreateParameterApproverBiayaMateraiCommand;
import bank_mega.corsys.application.parameter.approverbiayamaterai.dto.ParameterApproverBiayaMateraiResponse;
import bank_mega.corsys.domain.exception.DomainRuleViolationException;
import bank_mega.corsys.domain.model.common.AuditTrail;
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
public class CreateParameterApproverBiayaMateraiUseCase {

    private final ParameterApproverBiayaMateraiRepository parameterApproverBiayaMateraiRepository;

    @Transactional
    public ParameterApproverBiayaMateraiResponse execute(CreateParameterApproverBiayaMateraiCommand command, User authPrincipal) {
        // Validate code uniqueness
        parameterApproverBiayaMateraiRepository.findFirstByCode(new ParameterApproverBiayaMateraiCode(command.code()))
                .ifPresent(existing -> {
                    throw new DomainRuleViolationException("ParameterApproverBiayaMaterai code already exists: " + command.code());
                });

        // Create new parameter
        ParameterApproverBiayaMaterai newParameter = new ParameterApproverBiayaMaterai(
                new ParameterApproverBiayaMateraiId(command.code()),
                new ParameterApproverBiayaMateraiCode(command.code()),
                new ParameterApproverBiayaMateraiValue(command.value()),
                AuditTrail.create(authPrincipal.getId().value())
        );

        // Save to repository
        ParameterApproverBiayaMaterai saved = parameterApproverBiayaMateraiRepository.save(newParameter);

        // Convert to response DTO
        return ParameterApproverBiayaMateraiResponse.builder()
                .code(saved.getCode().value())
                .value(saved.getValue().value())
                .build();
    }

}
