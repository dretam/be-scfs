package bank_mega.corsys.application.parameter.jenistransaksirtgs.usecase;

import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.application.parameter.jenistransaksirtgs.dto.ParameterJenisTransaksiRtgsResponse;
import bank_mega.corsys.domain.exception.ParameterJenisTransaksiRtgsNotFoundException;
import bank_mega.corsys.domain.model.parameter.jenistransaksirtgs.ParameterJenisTransaksiRtgs;
import bank_mega.corsys.domain.model.parameter.jenistransaksirtgs.ParameterJenisTransaksiRtgsId;
import bank_mega.corsys.domain.repository.ParameterJenisTransaksiRtgsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
public class RetrieveParameterJenisTransaksiRtgsUseCase {

    private final ParameterJenisTransaksiRtgsRepository parameterJenisTransaksiRtgsRepository;

    @Transactional(readOnly = true)
    public ParameterJenisTransaksiRtgsResponse execute(Integer code) {
        ParameterJenisTransaksiRtgs parameter = parameterJenisTransaksiRtgsRepository.findFirstByIdAndAuditDeletedAtIsNull(
                        new ParameterJenisTransaksiRtgsId(code))
                .orElseThrow(() -> new ParameterJenisTransaksiRtgsNotFoundException(new ParameterJenisTransaksiRtgsId(code)));

        return ParameterJenisTransaksiRtgsResponse.builder()
                .code(parameter.getCode().value())
                .value(parameter.getValue().value())
                .build();
    }

}
