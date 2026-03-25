package bank_mega.corsys.application.parameter.sumberdana.usecase;

import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.application.parameter.sumberdana.command.SoftDeleteParameterSumberDanaCommand;
import bank_mega.corsys.application.parameter.sumberdana.dto.ParameterSumberDanaResponse;
import bank_mega.corsys.domain.exception.ParameterSumberDanaNotFoundException;
import bank_mega.corsys.domain.model.parameter.sumberdana.ParameterSumberDana;
import bank_mega.corsys.domain.model.parameter.sumberdana.ParameterSumberDanaId;
import bank_mega.corsys.domain.model.user.User;
import bank_mega.corsys.domain.repository.ParameterSumberDanaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
public class SoftDeleteParameterSumberDanaUseCase {

    private final ParameterSumberDanaRepository parameterSumberDanaRepository;

    @Transactional
    public ParameterSumberDanaResponse execute(SoftDeleteParameterSumberDanaCommand command, User authPrincipal) {
        ParameterSumberDana parameter = parameterSumberDanaRepository.findFirstByIdAndAuditDeletedAtIsNull(
                        new ParameterSumberDanaId(command.code()))
                .orElseThrow(() -> new ParameterSumberDanaNotFoundException(new ParameterSumberDanaId(command.code())));

        parameter.deleteAudit(authPrincipal.getId().value());

        ParameterSumberDana deleted = parameterSumberDanaRepository.save(parameter);

        return ParameterSumberDanaResponse.builder()
                .code(deleted.getCode().value())
                .value(deleted.getValue().value())
                .build();
    }

}
