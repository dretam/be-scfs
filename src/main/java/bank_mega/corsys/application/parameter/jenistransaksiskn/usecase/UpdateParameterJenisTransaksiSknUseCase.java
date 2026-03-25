package bank_mega.corsys.application.parameter.jenistransaksiskn.usecase;

import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.application.parameter.jenistransaksiskn.command.UpdateParameterJenisTransaksiSknCommand;
import bank_mega.corsys.application.parameter.jenistransaksiskn.dto.ParameterJenisTransaksiSknResponse;
import bank_mega.corsys.domain.exception.DomainRuleViolationException;
import bank_mega.corsys.domain.exception.ParameterJenisTransaksiSknNotFoundException;
import bank_mega.corsys.domain.model.parameter.jenistransaksiskn.ParameterJenisTransaksiSkn;
import bank_mega.corsys.domain.model.parameter.jenistransaksiskn.ParameterJenisTransaksiSknCode;
import bank_mega.corsys.domain.model.parameter.jenistransaksiskn.ParameterJenisTransaksiSknId;
import bank_mega.corsys.domain.model.parameter.jenistransaksiskn.ParameterJenisTransaksiSknValue;
import bank_mega.corsys.domain.model.user.User;
import bank_mega.corsys.domain.repository.ParameterJenisTransaksiSknRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
public class UpdateParameterJenisTransaksiSknUseCase {

    private final ParameterJenisTransaksiSknRepository parameterJenisTransaksiSknRepository;

    @Transactional
    public ParameterJenisTransaksiSknResponse execute(UpdateParameterJenisTransaksiSknCommand command, User authPrincipal) {
        // Find existing parameter
        ParameterJenisTransaksiSkn parameter = parameterJenisTransaksiSknRepository.findFirstByIdAndAuditDeletedAtIsNull(
                        new ParameterJenisTransaksiSknId(command.code()))
                .orElseThrow(() -> new ParameterJenisTransaksiSknNotFoundException(new ParameterJenisTransaksiSknId(command.code())));

        // Update fields
        parameter.changeValue(new ParameterJenisTransaksiSknValue(command.value()));

        // Update audit
        parameter.updateAudit(authPrincipal.getId().value());

        // Save to repository
        ParameterJenisTransaksiSkn saved = parameterJenisTransaksiSknRepository.save(parameter);

        // Convert to response DTO
        return ParameterJenisTransaksiSknResponse.builder()
                .code(saved.getCode().value())
                .value(saved.getValue().value())
                .build();
    }

}
