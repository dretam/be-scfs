package bank_mega.corsys.application.parameter.sumberdana.usecase;

import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.domain.exception.ParameterSumberDanaNotFoundException;
import bank_mega.corsys.domain.model.parameter.sumberdana.ParameterSumberDana;
import bank_mega.corsys.domain.model.parameter.sumberdana.ParameterSumberDanaId;
import bank_mega.corsys.domain.repository.ParameterSumberDanaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
public class DeleteParameterSumberDanaUseCase {

    private final ParameterSumberDanaRepository parameterSumberDanaRepository;

    @Transactional
    public ParameterSumberDanaId execute(String code) {
        ParameterSumberDanaId parameterId = new ParameterSumberDanaId(code);

        ParameterSumberDana parameter = parameterSumberDanaRepository.findFirstById(parameterId)
                .orElseThrow(() -> new ParameterSumberDanaNotFoundException(parameterId));

        parameterSumberDanaRepository.delete(parameter);

        return parameterId;
    }

}
