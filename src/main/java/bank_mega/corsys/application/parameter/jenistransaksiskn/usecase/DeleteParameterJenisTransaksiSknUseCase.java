package bank_mega.corsys.application.parameter.jenistransaksiskn.usecase;

import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.domain.exception.ParameterJenisTransaksiSknNotFoundException;
import bank_mega.corsys.domain.model.parameter.jenistransaksiskn.ParameterJenisTransaksiSkn;
import bank_mega.corsys.domain.model.parameter.jenistransaksiskn.ParameterJenisTransaksiSknId;
import bank_mega.corsys.domain.repository.ParameterJenisTransaksiSknRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
public class DeleteParameterJenisTransaksiSknUseCase {

    private final ParameterJenisTransaksiSknRepository parameterJenisTransaksiSknRepository;

    @Transactional
    public ParameterJenisTransaksiSknId execute(Integer code) {
        ParameterJenisTransaksiSknId parameterId = new ParameterJenisTransaksiSknId(code);

        ParameterJenisTransaksiSkn parameter = parameterJenisTransaksiSknRepository.findFirstById(parameterId)
                .orElseThrow(() -> new ParameterJenisTransaksiSknNotFoundException(parameterId));

        parameterJenisTransaksiSknRepository.delete(parameter);

        return parameterId;
    }

}
