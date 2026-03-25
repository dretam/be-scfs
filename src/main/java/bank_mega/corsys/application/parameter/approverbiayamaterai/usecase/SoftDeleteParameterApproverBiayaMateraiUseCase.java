package bank_mega.corsys.application.parameter.approverbiayamaterai.usecase;

import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.application.parameter.approverbiayamaterai.command.SoftDeleteParameterApproverBiayaMateraiCommand;
import bank_mega.corsys.application.parameter.approverbiayamaterai.dto.ParameterApproverBiayaMateraiResponse;
import bank_mega.corsys.domain.exception.ParameterApproverBiayaMateraiNotFoundException;
import bank_mega.corsys.domain.model.parameter.approverbiayamaterai.ParameterApproverBiayaMaterai;
import bank_mega.corsys.domain.model.parameter.approverbiayamaterai.ParameterApproverBiayaMateraiId;
import bank_mega.corsys.domain.model.user.User;
import bank_mega.corsys.domain.repository.ParameterApproverBiayaMateraiRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
public class SoftDeleteParameterApproverBiayaMateraiUseCase {

    private final ParameterApproverBiayaMateraiRepository parameterApproverBiayaMateraiRepository;

    @Transactional
    public ParameterApproverBiayaMateraiResponse execute(SoftDeleteParameterApproverBiayaMateraiCommand command, User authPrincipal) {
        ParameterApproverBiayaMaterai parameter = parameterApproverBiayaMateraiRepository.findFirstByIdAndAuditDeletedAtIsNull(
                        new ParameterApproverBiayaMateraiId(command.code()))
                .orElseThrow(() -> new ParameterApproverBiayaMateraiNotFoundException(new ParameterApproverBiayaMateraiId(command.code())));

        parameter.deleteAudit(authPrincipal.getId().value());

        ParameterApproverBiayaMaterai deleted = parameterApproverBiayaMateraiRepository.save(parameter);

        return ParameterApproverBiayaMateraiResponse.builder()
                .code(deleted.getCode().value())
                .value(deleted.getValue().value())
                .build();
    }

}
