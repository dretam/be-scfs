package bank_mega.corsys.application.parameter.jenistransaksirtgs.usecase;

import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.application.parameter.jenistransaksirtgs.command.SoftDeleteParameterJenisTransaksiRtgsCommand;
import bank_mega.corsys.application.parameter.jenistransaksirtgs.dto.ParameterJenisTransaksiRtgsResponse;
import bank_mega.corsys.domain.exception.ParameterJenisTransaksiRtgsNotFoundException;
import bank_mega.corsys.domain.model.parameter.jenistransaksirtgs.ParameterJenisTransaksiRtgs;
import bank_mega.corsys.domain.model.parameter.jenistransaksirtgs.ParameterJenisTransaksiRtgsId;
import bank_mega.corsys.domain.model.user.User;
import bank_mega.corsys.domain.repository.ParameterJenisTransaksiRtgsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
public class SoftDeleteParameterJenisTransaksiRtgsUseCase {

    private final ParameterJenisTransaksiRtgsRepository parameterJenisTransaksiRtgsRepository;

    @Transactional
    public ParameterJenisTransaksiRtgsResponse execute(SoftDeleteParameterJenisTransaksiRtgsCommand command, User authPrincipal) {
        ParameterJenisTransaksiRtgs parameter = parameterJenisTransaksiRtgsRepository.findFirstByIdAndAuditDeletedAtIsNull(
                        new ParameterJenisTransaksiRtgsId(command.code()))
                .orElseThrow(() -> new ParameterJenisTransaksiRtgsNotFoundException(new ParameterJenisTransaksiRtgsId(command.code())));

        parameter.deleteAudit(authPrincipal.getId().value());

        ParameterJenisTransaksiRtgs deleted = parameterJenisTransaksiRtgsRepository.save(parameter);

        return ParameterJenisTransaksiRtgsResponse.builder()
                .code(deleted.getCode().value())
                .value(deleted.getValue().value())
                .build();
    }

}
