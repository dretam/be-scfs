package bank_mega.corsys.application.parameter.automatictransfer.usecase;

import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.application.parameter.automatictransfer.dto.ParameterAutomaticTransferResponse;
import bank_mega.corsys.domain.exception.ParameterAutomaticTransferNotFoundException;
import bank_mega.corsys.domain.model.parameter.automatictransfer.ParameterAutomaticTransfer;
import bank_mega.corsys.domain.model.parameter.automatictransfer.ParameterAutomaticTransferId;
import bank_mega.corsys.domain.repository.ParameterAutomaticTransferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
public class RetrieveParameterAutomaticTransferUseCase {

    private final ParameterAutomaticTransferRepository parameterAutomaticTransferRepository;

    @Transactional(readOnly = true)
    public ParameterAutomaticTransferResponse execute(String code) {
        ParameterAutomaticTransfer parameter = parameterAutomaticTransferRepository.findFirstByIdAndAuditDeletedAtIsNull(
                        new ParameterAutomaticTransferId(code))
                .orElseThrow(() -> new ParameterAutomaticTransferNotFoundException(new ParameterAutomaticTransferId(code)));

        return ParameterAutomaticTransferResponse.builder()
                .code(parameter.getCode().value())
                .value(parameter.getValue().value())
                .build();
    }

}
