package bank_mega.corsys.application.parameter.metodepembayaranpokok.usecase;

import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.application.parameter.metodepembayaranpokok.dto.ParameterMetodePembayaranPokokResponse;
import bank_mega.corsys.domain.exception.ParameterMetodePembayaranPokokNotFoundException;
import bank_mega.corsys.domain.model.parameter.metodepembayaranpokok.ParameterMetodePembayaranPokok;
import bank_mega.corsys.domain.model.parameter.metodepembayaranpokok.ParameterMetodePembayaranPokokId;
import bank_mega.corsys.domain.repository.ParameterMetodePembayaranPokokRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
public class RetrieveParameterMetodePembayaranPokokUseCase {

    private final ParameterMetodePembayaranPokokRepository parameterMetodePembayaranPokokRepository;

    @Transactional(readOnly = true)
    public ParameterMetodePembayaranPokokResponse execute(String code) {
        ParameterMetodePembayaranPokok parameter = parameterMetodePembayaranPokokRepository.findFirstByIdAndAuditDeletedAtIsNull(
                        new ParameterMetodePembayaranPokokId(code))
                .orElseThrow(() -> new ParameterMetodePembayaranPokokNotFoundException(new ParameterMetodePembayaranPokokId(code)));

        return ParameterMetodePembayaranPokokResponse.builder()
                .code(parameter.getCode().value())
                .value(parameter.getValue().value())
                .build();
    }

}
