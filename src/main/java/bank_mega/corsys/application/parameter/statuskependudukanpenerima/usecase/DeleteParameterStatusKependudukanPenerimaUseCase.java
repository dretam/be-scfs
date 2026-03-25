package bank_mega.corsys.application.parameter.statuskependudukanpenerima.usecase;

import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.domain.exception.ParameterStatusKependudukanPenerimaNotFoundException;
import bank_mega.corsys.domain.model.parameter.statuskependudukanpenerima.ParameterStatusKependudukanPenerima;
import bank_mega.corsys.domain.model.parameter.statuskependudukanpenerima.ParameterStatusKependudukanPenerimaId;
import bank_mega.corsys.domain.repository.ParameterStatusKependudukanPenerimaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
public class DeleteParameterStatusKependudukanPenerimaUseCase {

    private final ParameterStatusKependudukanPenerimaRepository parameterStatusKependudukanPenerimaRepository;

    @Transactional
    public ParameterStatusKependudukanPenerimaId execute(Integer code) {
        ParameterStatusKependudukanPenerimaId parameterId = new ParameterStatusKependudukanPenerimaId(code);

        ParameterStatusKependudukanPenerima parameter = parameterStatusKependudukanPenerimaRepository.findFirstById(parameterId)
                .orElseThrow(() -> new ParameterStatusKependudukanPenerimaNotFoundException(parameterId));

        parameterStatusKependudukanPenerimaRepository.delete(parameter);

        return parameterId;
    }

}
