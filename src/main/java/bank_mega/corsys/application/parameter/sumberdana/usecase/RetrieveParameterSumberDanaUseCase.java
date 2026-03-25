package bank_mega.corsys.application.parameter.sumberdana.usecase;

import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.application.parameter.sumberdana.dto.ParameterSumberDanaResponse;
import bank_mega.corsys.domain.exception.ParameterSumberDanaNotFoundException;
import bank_mega.corsys.domain.model.parameter.sumberdana.ParameterSumberDana;
import bank_mega.corsys.domain.model.parameter.sumberdana.ParameterSumberDanaId;
import bank_mega.corsys.domain.repository.ParameterSumberDanaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
public class RetrieveParameterSumberDanaUseCase {

    private final ParameterSumberDanaRepository parameterSumberDanaRepository;

    @Transactional(readOnly = true)
    public ParameterSumberDanaResponse execute(String code) {
        ParameterSumberDana parameter = parameterSumberDanaRepository.findFirstByIdAndAuditDeletedAtIsNull(
                        new ParameterSumberDanaId(code))
                .orElseThrow(() -> new ParameterSumberDanaNotFoundException(new ParameterSumberDanaId(code)));

        return ParameterSumberDanaResponse.builder()
                .code(parameter.getCode().value())
                .value(parameter.getValue().value())
                .build();
    }

}
