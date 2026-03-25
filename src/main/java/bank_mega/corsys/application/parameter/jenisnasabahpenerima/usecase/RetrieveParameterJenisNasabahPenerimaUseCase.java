package bank_mega.corsys.application.parameter.jenisnasabahpenerima.usecase;

import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.application.parameter.jenisnasabahpenerima.dto.ParameterJenisNasabahPenerimaResponse;
import bank_mega.corsys.domain.exception.ParameterJenisNasabahPenerimaNotFoundException;
import bank_mega.corsys.domain.model.parameter.jenisnasabahpenerima.ParameterJenisNasabahPenerima;
import bank_mega.corsys.domain.model.parameter.jenisnasabahpenerima.ParameterJenisNasabahPenerimaId;
import bank_mega.corsys.domain.repository.ParameterJenisNasabahPenerimaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
public class RetrieveParameterJenisNasabahPenerimaUseCase {

    private final ParameterJenisNasabahPenerimaRepository parameterJenisNasabahPenerimaRepository;

    @Transactional(readOnly = true)
    public ParameterJenisNasabahPenerimaResponse execute(Integer code) {
        ParameterJenisNasabahPenerima parameter = parameterJenisNasabahPenerimaRepository.findFirstByIdAndAuditDeletedAtIsNull(
                        new ParameterJenisNasabahPenerimaId(code))
                .orElseThrow(() -> new ParameterJenisNasabahPenerimaNotFoundException(new ParameterJenisNasabahPenerimaId(code)));

        return ParameterJenisNasabahPenerimaResponse.builder()
                .code(parameter.getCode().value())
                .value(parameter.getValue().value())
                .build();
    }

}
