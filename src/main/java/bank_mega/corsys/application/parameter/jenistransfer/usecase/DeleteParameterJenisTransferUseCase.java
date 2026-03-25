package bank_mega.corsys.application.parameter.jenistransfer.usecase;

import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.domain.exception.ParameterJenisTransferNotFoundException;
import bank_mega.corsys.domain.model.parameter.jenistransfer.ParameterJenisTransfer;
import bank_mega.corsys.domain.model.parameter.jenistransfer.ParameterJenisTransferId;
import bank_mega.corsys.domain.repository.ParameterJenisTransferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
public class DeleteParameterJenisTransferUseCase {

    private final ParameterJenisTransferRepository parameterJenisTransferRepository;

    @Transactional
    public ParameterJenisTransferId execute(Integer code) {
        ParameterJenisTransferId parameterId = new ParameterJenisTransferId(code);

        ParameterJenisTransfer parameter = parameterJenisTransferRepository.findFirstById(parameterId)
                .orElseThrow(() -> new ParameterJenisTransferNotFoundException(parameterId));

        parameterJenisTransferRepository.delete(parameter);

        return parameterId;
    }

}
