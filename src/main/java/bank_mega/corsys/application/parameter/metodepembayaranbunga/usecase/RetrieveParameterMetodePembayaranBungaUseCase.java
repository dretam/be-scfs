package bank_mega.corsys.application.parameter.metodepembayaranbunga.usecase;

import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.application.parameter.metodepembayaranbunga.dto.ParameterMetodePembayaranBungaResponse;
import bank_mega.corsys.domain.exception.ParameterMetodePembayaranBungaNotFoundException;
import bank_mega.corsys.domain.model.parameter.metodepembayaranbunga.ParameterMetodePembayaranBunga;
import bank_mega.corsys.domain.model.parameter.metodepembayaranbunga.ParameterMetodePembayaranBungaId;
import bank_mega.corsys.domain.repository.ParameterMetodePembayaranBungaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
public class RetrieveParameterMetodePembayaranBungaUseCase {

    private final ParameterMetodePembayaranBungaRepository parameterMetodePembayaranBungaRepository;

    @Transactional(readOnly = true)
    public ParameterMetodePembayaranBungaResponse execute(String code) {
        ParameterMetodePembayaranBunga parameter = parameterMetodePembayaranBungaRepository.findFirstByIdAndAuditDeletedAtIsNull(
                        new ParameterMetodePembayaranBungaId(code))
                .orElseThrow(() -> new ParameterMetodePembayaranBungaNotFoundException(new ParameterMetodePembayaranBungaId(code)));

        return ParameterMetodePembayaranBungaResponse.builder()
                .code(parameter.getCode().value())
                .value(parameter.getValue().value())
                .build();
    }

}
