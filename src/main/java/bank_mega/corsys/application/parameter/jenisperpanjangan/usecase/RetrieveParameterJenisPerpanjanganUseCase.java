package bank_mega.corsys.application.parameter.jenisperpanjangan.usecase;

import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.application.parameter.jenisperpanjangan.dto.ParameterJenisPerpanjanganResponse;
import bank_mega.corsys.domain.exception.ParameterJenisPerpanjanganNotFoundException;
import bank_mega.corsys.domain.model.parameter.jenisperpanjangan.ParameterJenisPerpanjangan;
import bank_mega.corsys.domain.model.parameter.jenisperpanjangan.ParameterJenisPerpanjanganId;
import bank_mega.corsys.domain.repository.ParameterJenisPerpanjanganRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
public class RetrieveParameterJenisPerpanjanganUseCase {

    private final ParameterJenisPerpanjanganRepository parameterJenisPerpanjanganRepository;

    @Transactional(readOnly = true)
    public ParameterJenisPerpanjanganResponse execute(String code) {
        ParameterJenisPerpanjangan parameter = parameterJenisPerpanjanganRepository.findFirstByIdAndAuditDeletedAtIsNull(
                        new ParameterJenisPerpanjanganId(code))
                .orElseThrow(() -> new ParameterJenisPerpanjanganNotFoundException(new ParameterJenisPerpanjanganId(code)));

        return ParameterJenisPerpanjanganResponse.builder()
                .code(parameter.getCode().value())
                .value(parameter.getValue().value())
                .build();
    }

}
