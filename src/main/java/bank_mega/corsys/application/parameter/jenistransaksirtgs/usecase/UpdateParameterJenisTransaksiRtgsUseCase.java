package bank_mega.corsys.application.parameter.jenistransaksirtgs.usecase;

import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.application.parameter.jenistransaksirtgs.command.UpdateParameterJenisTransaksiRtgsCommand;
import bank_mega.corsys.application.parameter.jenistransaksirtgs.dto.ParameterJenisTransaksiRtgsResponse;
import bank_mega.corsys.domain.exception.DomainRuleViolationException;
import bank_mega.corsys.domain.exception.ParameterJenisTransaksiRtgsNotFoundException;
import bank_mega.corsys.domain.model.parameter.jenistransaksirtgs.ParameterJenisTransaksiRtgs;
import bank_mega.corsys.domain.model.parameter.jenistransaksirtgs.ParameterJenisTransaksiRtgsCode;
import bank_mega.corsys.domain.model.parameter.jenistransaksirtgs.ParameterJenisTransaksiRtgsId;
import bank_mega.corsys.domain.model.parameter.jenistransaksirtgs.ParameterJenisTransaksiRtgsValue;
import bank_mega.corsys.domain.model.user.User;
import bank_mega.corsys.domain.repository.ParameterJenisTransaksiRtgsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
public class UpdateParameterJenisTransaksiRtgsUseCase {

    private final ParameterJenisTransaksiRtgsRepository parameterJenisTransaksiRtgsRepository;

    @Transactional
    public ParameterJenisTransaksiRtgsResponse execute(UpdateParameterJenisTransaksiRtgsCommand command, User authPrincipal) {
        // Find existing parameter
        ParameterJenisTransaksiRtgs parameter = parameterJenisTransaksiRtgsRepository.findFirstByIdAndAuditDeletedAtIsNull(
                        new ParameterJenisTransaksiRtgsId(command.code()))
                .orElseThrow(() -> new ParameterJenisTransaksiRtgsNotFoundException(new ParameterJenisTransaksiRtgsId(command.code())));

        // Update fields
        parameter.changeValue(new ParameterJenisTransaksiRtgsValue(command.value()));

        // Update audit
        parameter.updateAudit(authPrincipal.getId().value());

        // Save to repository
        ParameterJenisTransaksiRtgs saved = parameterJenisTransaksiRtgsRepository.save(parameter);

        // Convert to response DTO
        return ParameterJenisTransaksiRtgsResponse.builder()
                .code(saved.getCode().value())
                .value(saved.getValue().value())
                .build();
    }

}
