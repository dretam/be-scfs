package bank_mega.corsys.application.parameter.jenistransaksirtgs.usecase;

import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.domain.exception.ParameterJenisTransaksiRtgsNotFoundException;
import bank_mega.corsys.domain.model.parameter.jenistransaksirtgs.ParameterJenisTransaksiRtgs;
import bank_mega.corsys.domain.model.parameter.jenistransaksirtgs.ParameterJenisTransaksiRtgsId;
import bank_mega.corsys.domain.repository.ParameterJenisTransaksiRtgsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
public class DeleteParameterJenisTransaksiRtgsUseCase {

    private final ParameterJenisTransaksiRtgsRepository parameterJenisTransaksiRtgsRepository;

    @Transactional
    public ParameterJenisTransaksiRtgsId execute(Integer code) {
        ParameterJenisTransaksiRtgsId parameterId = new ParameterJenisTransaksiRtgsId(code);

        ParameterJenisTransaksiRtgs parameter = parameterJenisTransaksiRtgsRepository.findFirstById(parameterId)
                .orElseThrow(() -> new ParameterJenisTransaksiRtgsNotFoundException(parameterId));

        parameterJenisTransaksiRtgsRepository.delete(parameter);

        return parameterId;
    }

}
