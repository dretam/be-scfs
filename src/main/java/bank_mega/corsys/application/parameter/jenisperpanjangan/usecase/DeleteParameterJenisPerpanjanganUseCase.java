package bank_mega.corsys.application.parameter.jenisperpanjangan.usecase;

import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.domain.exception.ParameterJenisPerpanjanganNotFoundException;
import bank_mega.corsys.domain.model.parameter.jenisperpanjangan.ParameterJenisPerpanjangan;
import bank_mega.corsys.domain.model.parameter.jenisperpanjangan.ParameterJenisPerpanjanganId;
import bank_mega.corsys.domain.repository.ParameterJenisPerpanjanganRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
public class DeleteParameterJenisPerpanjanganUseCase {

    private final ParameterJenisPerpanjanganRepository parameterJenisPerpanjanganRepository;

    @Transactional
    public ParameterJenisPerpanjanganId execute(String code) {
        ParameterJenisPerpanjanganId parameterId = new ParameterJenisPerpanjanganId(code);

        ParameterJenisPerpanjangan parameter = parameterJenisPerpanjanganRepository.findFirstById(parameterId)
                .orElseThrow(() -> new ParameterJenisPerpanjanganNotFoundException(parameterId));

        parameterJenisPerpanjanganRepository.delete(parameter);

        return parameterId;
    }

}
