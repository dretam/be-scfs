package bank_mega.corsys.application.parameter.metodepembayaranpokok.usecase;

import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.domain.exception.ParameterMetodePembayaranPokokNotFoundException;
import bank_mega.corsys.domain.model.parameter.metodepembayaranpokok.ParameterMetodePembayaranPokok;
import bank_mega.corsys.domain.model.parameter.metodepembayaranpokok.ParameterMetodePembayaranPokokId;
import bank_mega.corsys.domain.repository.ParameterMetodePembayaranPokokRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
public class DeleteParameterMetodePembayaranPokokUseCase {

    private final ParameterMetodePembayaranPokokRepository parameterMetodePembayaranPokokRepository;

    @Transactional
    public ParameterMetodePembayaranPokokId execute(String code) {
        ParameterMetodePembayaranPokokId parameterId = new ParameterMetodePembayaranPokokId(code);

        ParameterMetodePembayaranPokok parameter = parameterMetodePembayaranPokokRepository.findFirstById(parameterId)
                .orElseThrow(() -> new ParameterMetodePembayaranPokokNotFoundException(parameterId));

        parameterMetodePembayaranPokokRepository.delete(parameter);

        return parameterId;
    }

}
