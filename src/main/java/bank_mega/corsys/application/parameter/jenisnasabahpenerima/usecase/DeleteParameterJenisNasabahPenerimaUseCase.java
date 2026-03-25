package bank_mega.corsys.application.parameter.jenisnasabahpenerima.usecase;

import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.domain.exception.ParameterJenisNasabahPenerimaNotFoundException;
import bank_mega.corsys.domain.model.parameter.jenisnasabahpenerima.ParameterJenisNasabahPenerima;
import bank_mega.corsys.domain.model.parameter.jenisnasabahpenerima.ParameterJenisNasabahPenerimaId;
import bank_mega.corsys.domain.repository.ParameterJenisNasabahPenerimaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
public class DeleteParameterJenisNasabahPenerimaUseCase {

    private final ParameterJenisNasabahPenerimaRepository parameterJenisNasabahPenerimaRepository;

    @Transactional
    public ParameterJenisNasabahPenerimaId execute(Integer code) {
        ParameterJenisNasabahPenerimaId parameterId = new ParameterJenisNasabahPenerimaId(code);

        ParameterJenisNasabahPenerima parameter = parameterJenisNasabahPenerimaRepository.findFirstById(parameterId)
                .orElseThrow(() -> new ParameterJenisNasabahPenerimaNotFoundException(parameterId));

        parameterJenisNasabahPenerimaRepository.delete(parameter);

        return parameterId;
    }

}
