package bank_mega.corsys.application.parameter.jenisperpanjangan.usecase;

import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.application.parameter.jenisperpanjangan.command.SoftDeleteParameterJenisPerpanjanganCommand;
import bank_mega.corsys.application.parameter.jenisperpanjangan.dto.ParameterJenisPerpanjanganResponse;
import bank_mega.corsys.domain.exception.ParameterJenisPerpanjanganNotFoundException;
import bank_mega.corsys.domain.model.parameter.jenisperpanjangan.ParameterJenisPerpanjangan;
import bank_mega.corsys.domain.model.parameter.jenisperpanjangan.ParameterJenisPerpanjanganId;
import bank_mega.corsys.domain.model.user.User;
import bank_mega.corsys.domain.repository.ParameterJenisPerpanjanganRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
public class SoftDeleteParameterJenisPerpanjanganUseCase {

    private final ParameterJenisPerpanjanganRepository parameterJenisPerpanjanganRepository;

    @Transactional
    public ParameterJenisPerpanjanganResponse execute(SoftDeleteParameterJenisPerpanjanganCommand command, User authPrincipal) {
        ParameterJenisPerpanjangan parameter = parameterJenisPerpanjanganRepository.findFirstByIdAndAuditDeletedAtIsNull(
                        new ParameterJenisPerpanjanganId(command.code()))
                .orElseThrow(() -> new ParameterJenisPerpanjanganNotFoundException(new ParameterJenisPerpanjanganId(command.code())));

        parameter.deleteAudit(authPrincipal.getId().value());

        ParameterJenisPerpanjangan deleted = parameterJenisPerpanjanganRepository.save(parameter);

        return ParameterJenisPerpanjanganResponse.builder()
                .code(deleted.getCode().value())
                .value(deleted.getValue().value())
                .build();
    }

}
