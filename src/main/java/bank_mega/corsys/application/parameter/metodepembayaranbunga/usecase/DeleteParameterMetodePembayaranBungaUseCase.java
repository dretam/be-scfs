package bank_mega.corsys.application.parameter.metodepembayaranbunga.usecase;

import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.domain.exception.ParameterMetodePembayaranBungaNotFoundException;
import bank_mega.corsys.domain.model.parameter.metodepembayaranbunga.ParameterMetodePembayaranBunga;
import bank_mega.corsys.domain.model.parameter.metodepembayaranbunga.ParameterMetodePembayaranBungaId;
import bank_mega.corsys.domain.repository.ParameterMetodePembayaranBungaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
public class DeleteParameterMetodePembayaranBungaUseCase {

    private final ParameterMetodePembayaranBungaRepository parameterMetodePembayaranBungaRepository;

    @Transactional
    public ParameterMetodePembayaranBungaId execute(String code) {
        ParameterMetodePembayaranBungaId parameterId = new ParameterMetodePembayaranBungaId(code);

        ParameterMetodePembayaranBunga parameter = parameterMetodePembayaranBungaRepository.findFirstById(parameterId)
                .orElseThrow(() -> new ParameterMetodePembayaranBungaNotFoundException(parameterId));

        parameterMetodePembayaranBungaRepository.delete(parameter);

        return parameterId;
    }

}
