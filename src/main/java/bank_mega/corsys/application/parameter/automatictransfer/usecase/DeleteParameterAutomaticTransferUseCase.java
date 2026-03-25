package bank_mega.corsys.application.parameter.automatictransfer.usecase;

import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.domain.exception.ParameterAutomaticTransferNotFoundException;
import bank_mega.corsys.domain.model.parameter.automatictransfer.ParameterAutomaticTransfer;
import bank_mega.corsys.domain.model.parameter.automatictransfer.ParameterAutomaticTransferId;
import bank_mega.corsys.domain.repository.ParameterAutomaticTransferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
public class DeleteParameterAutomaticTransferUseCase {

    private final ParameterAutomaticTransferRepository parameterAutomaticTransferRepository;

    @Transactional
    public ParameterAutomaticTransferId execute(String code) {
        ParameterAutomaticTransferId parameterId = new ParameterAutomaticTransferId(code);

        ParameterAutomaticTransfer parameter = parameterAutomaticTransferRepository.findFirstById(parameterId)
                .orElseThrow(() -> new ParameterAutomaticTransferNotFoundException(parameterId));

        parameterAutomaticTransferRepository.delete(parameter);

        return parameterId;
    }

}
