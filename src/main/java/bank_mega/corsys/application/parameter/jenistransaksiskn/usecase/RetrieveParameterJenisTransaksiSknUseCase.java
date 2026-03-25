package bank_mega.corsys.application.parameter.jenistransaksiskn.usecase;

import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.application.parameter.jenistransaksiskn.dto.ParameterJenisTransaksiSknResponse;
import bank_mega.corsys.domain.exception.ParameterJenisTransaksiSknNotFoundException;
import bank_mega.corsys.domain.model.parameter.jenistransaksiskn.ParameterJenisTransaksiSkn;
import bank_mega.corsys.domain.model.parameter.jenistransaksiskn.ParameterJenisTransaksiSknId;
import bank_mega.corsys.domain.repository.ParameterJenisTransaksiSknRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
public class RetrieveParameterJenisTransaksiSknUseCase {

    private final ParameterJenisTransaksiSknRepository parameterJenisTransaksiSknRepository;

    @Transactional(readOnly = true)
    public ParameterJenisTransaksiSknResponse execute(Integer code) {
        ParameterJenisTransaksiSkn parameter = parameterJenisTransaksiSknRepository.findFirstByIdAndAuditDeletedAtIsNull(
                        new ParameterJenisTransaksiSknId(code))
                .orElseThrow(() -> new ParameterJenisTransaksiSknNotFoundException(new ParameterJenisTransaksiSknId(code)));

        return ParameterJenisTransaksiSknResponse.builder()
                .code(parameter.getCode().value())
                .value(parameter.getValue().value())
                .build();
    }

}
