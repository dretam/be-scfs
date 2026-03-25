package bank_mega.corsys.application.parameter.jenisnasabahpenerima.usecase;

import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.application.parameter.jenisnasabahpenerima.command.SoftDeleteParameterJenisNasabahPenerimaCommand;
import bank_mega.corsys.application.parameter.jenisnasabahpenerima.dto.ParameterJenisNasabahPenerimaResponse;
import bank_mega.corsys.domain.exception.ParameterJenisNasabahPenerimaNotFoundException;
import bank_mega.corsys.domain.model.parameter.jenisnasabahpenerima.ParameterJenisNasabahPenerima;
import bank_mega.corsys.domain.model.parameter.jenisnasabahpenerima.ParameterJenisNasabahPenerimaId;
import bank_mega.corsys.domain.model.user.User;
import bank_mega.corsys.domain.repository.ParameterJenisNasabahPenerimaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
public class SoftDeleteParameterJenisNasabahPenerimaUseCase {

    private final ParameterJenisNasabahPenerimaRepository parameterJenisNasabahPenerimaRepository;

    @Transactional
    public ParameterJenisNasabahPenerimaResponse execute(SoftDeleteParameterJenisNasabahPenerimaCommand command, User authPrincipal) {
        ParameterJenisNasabahPenerima parameter = parameterJenisNasabahPenerimaRepository.findFirstByIdAndAuditDeletedAtIsNull(
                        new ParameterJenisNasabahPenerimaId(command.code()))
                .orElseThrow(() -> new ParameterJenisNasabahPenerimaNotFoundException(new ParameterJenisNasabahPenerimaId(command.code())));

        parameter.deleteAudit(authPrincipal.getId().value());

        ParameterJenisNasabahPenerima deleted = parameterJenisNasabahPenerimaRepository.save(parameter);

        return ParameterJenisNasabahPenerimaResponse.builder()
                .code(deleted.getCode().value())
                .value(deleted.getValue().value())
                .build();
    }

}
