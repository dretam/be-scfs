package bank_mega.corsys.application.parameter.metodepembayaranpokok.usecase;

import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.application.parameter.metodepembayaranpokok.command.SoftDeleteParameterMetodePembayaranPokokCommand;
import bank_mega.corsys.application.parameter.metodepembayaranpokok.dto.ParameterMetodePembayaranPokokResponse;
import bank_mega.corsys.domain.exception.ParameterMetodePembayaranPokokNotFoundException;
import bank_mega.corsys.domain.model.parameter.metodepembayaranpokok.ParameterMetodePembayaranPokok;
import bank_mega.corsys.domain.model.parameter.metodepembayaranpokok.ParameterMetodePembayaranPokokId;
import bank_mega.corsys.domain.model.user.User;
import bank_mega.corsys.domain.repository.ParameterMetodePembayaranPokokRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
public class SoftDeleteParameterMetodePembayaranPokokUseCase {

    private final ParameterMetodePembayaranPokokRepository parameterMetodePembayaranPokokRepository;

    @Transactional
    public ParameterMetodePembayaranPokokResponse execute(SoftDeleteParameterMetodePembayaranPokokCommand command, User authPrincipal) {
        ParameterMetodePembayaranPokok parameter = parameterMetodePembayaranPokokRepository.findFirstByIdAndAuditDeletedAtIsNull(
                        new ParameterMetodePembayaranPokokId(command.code()))
                .orElseThrow(() -> new ParameterMetodePembayaranPokokNotFoundException(new ParameterMetodePembayaranPokokId(command.code())));

        parameter.deleteAudit(authPrincipal.getId().value());

        ParameterMetodePembayaranPokok deleted = parameterMetodePembayaranPokokRepository.save(parameter);

        return ParameterMetodePembayaranPokokResponse.builder()
                .code(deleted.getCode().value())
                .value(deleted.getValue().value())
                .build();
    }

}
