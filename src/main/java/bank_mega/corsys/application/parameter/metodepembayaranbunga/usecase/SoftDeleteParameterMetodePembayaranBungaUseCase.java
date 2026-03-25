package bank_mega.corsys.application.parameter.metodepembayaranbunga.usecase;

import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.application.parameter.metodepembayaranbunga.command.SoftDeleteParameterMetodePembayaranBungaCommand;
import bank_mega.corsys.application.parameter.metodepembayaranbunga.dto.ParameterMetodePembayaranBungaResponse;
import bank_mega.corsys.domain.exception.ParameterMetodePembayaranBungaNotFoundException;
import bank_mega.corsys.domain.model.parameter.metodepembayaranbunga.ParameterMetodePembayaranBunga;
import bank_mega.corsys.domain.model.parameter.metodepembayaranbunga.ParameterMetodePembayaranBungaId;
import bank_mega.corsys.domain.model.user.User;
import bank_mega.corsys.domain.repository.ParameterMetodePembayaranBungaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
public class SoftDeleteParameterMetodePembayaranBungaUseCase {

    private final ParameterMetodePembayaranBungaRepository parameterMetodePembayaranBungaRepository;

    @Transactional
    public ParameterMetodePembayaranBungaResponse execute(SoftDeleteParameterMetodePembayaranBungaCommand command, User authPrincipal) {
        ParameterMetodePembayaranBunga parameter = parameterMetodePembayaranBungaRepository.findFirstByIdAndAuditDeletedAtIsNull(
                        new ParameterMetodePembayaranBungaId(command.code()))
                .orElseThrow(() -> new ParameterMetodePembayaranBungaNotFoundException(new ParameterMetodePembayaranBungaId(command.code())));

        parameter.deleteAudit(authPrincipal.getId().value());

        ParameterMetodePembayaranBunga deleted = parameterMetodePembayaranBungaRepository.save(parameter);

        return ParameterMetodePembayaranBungaResponse.builder()
                .code(deleted.getCode().value())
                .value(deleted.getValue().value())
                .build();
    }

}
