package bank_mega.corsys.application.parameter.jenistransfer.usecase;

import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.application.parameter.jenistransfer.dto.ParameterJenisTransferResponse;
import bank_mega.corsys.domain.exception.ParameterJenisTransferNotFoundException;
import bank_mega.corsys.domain.model.parameter.jenistransfer.ParameterJenisTransfer;
import bank_mega.corsys.domain.model.parameter.jenistransfer.ParameterJenisTransferId;
import bank_mega.corsys.domain.repository.ParameterJenisTransferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
public class RetrieveParameterJenisTransferUseCase {

    private final ParameterJenisTransferRepository parameterJenisTransferRepository;

    @Transactional(readOnly = true)
    public ParameterJenisTransferResponse execute(Integer code) {
        ParameterJenisTransfer parameter = parameterJenisTransferRepository.findFirstByIdAndAuditDeletedAtIsNull(
                        new ParameterJenisTransferId(code))
                .orElseThrow(() -> new ParameterJenisTransferNotFoundException(new ParameterJenisTransferId(code)));

        return ParameterJenisTransferResponse.builder()
                .code(parameter.getCode().value())
                .value(parameter.getValue().value())
                .build();
    }

}
