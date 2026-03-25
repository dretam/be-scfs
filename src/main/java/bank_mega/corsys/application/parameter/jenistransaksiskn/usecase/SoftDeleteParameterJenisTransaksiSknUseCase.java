package bank_mega.corsys.application.parameter.jenistransaksiskn.usecase;

import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.application.parameter.jenistransaksiskn.command.SoftDeleteParameterJenisTransaksiSknCommand;
import bank_mega.corsys.application.parameter.jenistransaksiskn.dto.ParameterJenisTransaksiSknResponse;
import bank_mega.corsys.domain.exception.ParameterJenisTransaksiSknNotFoundException;
import bank_mega.corsys.domain.model.parameter.jenistransaksiskn.ParameterJenisTransaksiSkn;
import bank_mega.corsys.domain.model.parameter.jenistransaksiskn.ParameterJenisTransaksiSknId;
import bank_mega.corsys.domain.model.user.User;
import bank_mega.corsys.domain.repository.ParameterJenisTransaksiSknRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
public class SoftDeleteParameterJenisTransaksiSknUseCase {

    private final ParameterJenisTransaksiSknRepository parameterJenisTransaksiSknRepository;

    @Transactional
    public ParameterJenisTransaksiSknResponse execute(SoftDeleteParameterJenisTransaksiSknCommand command, User authPrincipal) {
        ParameterJenisTransaksiSkn parameter = parameterJenisTransaksiSknRepository.findFirstByIdAndAuditDeletedAtIsNull(
                        new ParameterJenisTransaksiSknId(command.code()))
                .orElseThrow(() -> new ParameterJenisTransaksiSknNotFoundException(new ParameterJenisTransaksiSknId(command.code())));

        parameter.deleteAudit(authPrincipal.getId().value());

        ParameterJenisTransaksiSkn deleted = parameterJenisTransaksiSknRepository.save(parameter);

        return ParameterJenisTransaksiSknResponse.builder()
                .code(deleted.getCode().value())
                .value(deleted.getValue().value())
                .build();
    }

}
