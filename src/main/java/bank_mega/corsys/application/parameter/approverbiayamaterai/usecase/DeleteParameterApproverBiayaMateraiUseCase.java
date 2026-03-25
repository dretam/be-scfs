package bank_mega.corsys.application.parameter.approverbiayamaterai.usecase;

import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.domain.exception.ParameterApproverBiayaMateraiNotFoundException;
import bank_mega.corsys.domain.model.parameter.approverbiayamaterai.ParameterApproverBiayaMaterai;
import bank_mega.corsys.domain.model.parameter.approverbiayamaterai.ParameterApproverBiayaMateraiId;
import bank_mega.corsys.domain.repository.ParameterApproverBiayaMateraiRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
public class DeleteParameterApproverBiayaMateraiUseCase {

    private final ParameterApproverBiayaMateraiRepository parameterApproverBiayaMateraiRepository;

    @Transactional
    public ParameterApproverBiayaMateraiId execute(String code) {
        ParameterApproverBiayaMateraiId parameterId = new ParameterApproverBiayaMateraiId(code);

        ParameterApproverBiayaMaterai parameter = parameterApproverBiayaMateraiRepository.findFirstById(parameterId)
                .orElseThrow(() -> new ParameterApproverBiayaMateraiNotFoundException(parameterId));

        parameterApproverBiayaMateraiRepository.delete(parameter);

        return parameterId;
    }

}
