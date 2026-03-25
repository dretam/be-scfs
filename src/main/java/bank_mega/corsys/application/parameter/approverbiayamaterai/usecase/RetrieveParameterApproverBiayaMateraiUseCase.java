package bank_mega.corsys.application.parameter.approverbiayamaterai.usecase;

import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.application.parameter.approverbiayamaterai.dto.ParameterApproverBiayaMateraiResponse;
import bank_mega.corsys.domain.exception.ParameterApproverBiayaMateraiNotFoundException;
import bank_mega.corsys.domain.model.parameter.approverbiayamaterai.ParameterApproverBiayaMaterai;
import bank_mega.corsys.domain.model.parameter.approverbiayamaterai.ParameterApproverBiayaMateraiId;
import bank_mega.corsys.domain.repository.ParameterApproverBiayaMateraiRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
public class RetrieveParameterApproverBiayaMateraiUseCase {

    private final ParameterApproverBiayaMateraiRepository parameterApproverBiayaMateraiRepository;

    @Transactional(readOnly = true)
    public ParameterApproverBiayaMateraiResponse execute(String code) {
        ParameterApproverBiayaMaterai parameter = parameterApproverBiayaMateraiRepository.findFirstByIdAndAuditDeletedAtIsNull(
                        new ParameterApproverBiayaMateraiId(code))
                .orElseThrow(() -> new ParameterApproverBiayaMateraiNotFoundException(new ParameterApproverBiayaMateraiId(code)));

        return ParameterApproverBiayaMateraiResponse.builder()
                .code(parameter.getCode().value())
                .value(parameter.getValue().value())
                .build();
    }

}
