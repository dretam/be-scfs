package bank_mega.corsys.application.parameter.statuskependudukanpenerima.usecase;

import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.application.parameter.statuskependudukanpenerima.command.SoftDeleteParameterStatusKependudukanPenerimaCommand;
import bank_mega.corsys.application.parameter.statuskependudukanpenerima.dto.ParameterStatusKependudukanPenerimaResponse;
import bank_mega.corsys.domain.exception.ParameterStatusKependudukanPenerimaNotFoundException;
import bank_mega.corsys.domain.model.parameter.statuskependudukanpenerima.ParameterStatusKependudukanPenerima;
import bank_mega.corsys.domain.model.parameter.statuskependudukanpenerima.ParameterStatusKependudukanPenerimaId;
import bank_mega.corsys.domain.model.user.User;
import bank_mega.corsys.domain.repository.ParameterStatusKependudukanPenerimaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
public class SoftDeleteParameterStatusKependudukanPenerimaUseCase {

    private final ParameterStatusKependudukanPenerimaRepository parameterStatusKependudukanPenerimaRepository;

    @Transactional
    public ParameterStatusKependudukanPenerimaResponse execute(SoftDeleteParameterStatusKependudukanPenerimaCommand command, User authPrincipal) {
        ParameterStatusKependudukanPenerima parameter = parameterStatusKependudukanPenerimaRepository.findFirstByIdAndAuditDeletedAtIsNull(
                        new ParameterStatusKependudukanPenerimaId(command.code()))
                .orElseThrow(() -> new ParameterStatusKependudukanPenerimaNotFoundException(new ParameterStatusKependudukanPenerimaId(command.code())));

        parameter.deleteAudit(authPrincipal.getId().value());

        ParameterStatusKependudukanPenerima deleted = parameterStatusKependudukanPenerimaRepository.save(parameter);

        return ParameterStatusKependudukanPenerimaResponse.builder()
                .code(deleted.getCode().value())
                .value(deleted.getValue().value())
                .build();
    }

}
