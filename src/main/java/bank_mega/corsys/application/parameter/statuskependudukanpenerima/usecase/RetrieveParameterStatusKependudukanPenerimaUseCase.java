package bank_mega.corsys.application.parameter.statuskependudukanpenerima.usecase;

import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.application.parameter.statuskependudukanpenerima.dto.ParameterStatusKependudukanPenerimaResponse;
import bank_mega.corsys.domain.exception.ParameterStatusKependudukanPenerimaNotFoundException;
import bank_mega.corsys.domain.model.parameter.statuskependudukanpenerima.ParameterStatusKependudukanPenerima;
import bank_mega.corsys.domain.model.parameter.statuskependudukanpenerima.ParameterStatusKependudukanPenerimaId;
import bank_mega.corsys.domain.repository.ParameterStatusKependudukanPenerimaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
public class RetrieveParameterStatusKependudukanPenerimaUseCase {

    private final ParameterStatusKependudukanPenerimaRepository parameterStatusKependudukanPenerimaRepository;

    @Transactional(readOnly = true)
    public ParameterStatusKependudukanPenerimaResponse execute(Integer code) {
        ParameterStatusKependudukanPenerima parameter = parameterStatusKependudukanPenerimaRepository.findFirstByIdAndAuditDeletedAtIsNull(
                        new ParameterStatusKependudukanPenerimaId(code))
                .orElseThrow(() -> new ParameterStatusKependudukanPenerimaNotFoundException(new ParameterStatusKependudukanPenerimaId(code)));

        return ParameterStatusKependudukanPenerimaResponse.builder()
                .code(parameter.getCode().value())
                .value(parameter.getValue().value())
                .build();
    }

}
