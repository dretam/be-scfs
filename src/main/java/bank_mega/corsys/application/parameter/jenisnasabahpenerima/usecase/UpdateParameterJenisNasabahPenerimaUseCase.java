package bank_mega.corsys.application.parameter.jenisnasabahpenerima.usecase;

import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.application.parameter.jenisnasabahpenerima.command.UpdateParameterJenisNasabahPenerimaCommand;
import bank_mega.corsys.application.parameter.jenisnasabahpenerima.dto.ParameterJenisNasabahPenerimaResponse;
import bank_mega.corsys.domain.exception.DomainRuleViolationException;
import bank_mega.corsys.domain.exception.ParameterJenisNasabahPenerimaNotFoundException;
import bank_mega.corsys.domain.model.parameter.jenisnasabahpenerima.ParameterJenisNasabahPenerima;
import bank_mega.corsys.domain.model.parameter.jenisnasabahpenerima.ParameterJenisNasabahPenerimaCode;
import bank_mega.corsys.domain.model.parameter.jenisnasabahpenerima.ParameterJenisNasabahPenerimaId;
import bank_mega.corsys.domain.model.parameter.jenisnasabahpenerima.ParameterJenisNasabahPenerimaValue;
import bank_mega.corsys.domain.model.user.User;
import bank_mega.corsys.domain.repository.ParameterJenisNasabahPenerimaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
public class UpdateParameterJenisNasabahPenerimaUseCase {

    private final ParameterJenisNasabahPenerimaRepository parameterJenisNasabahPenerimaRepository;

    @Transactional
    public ParameterJenisNasabahPenerimaResponse execute(UpdateParameterJenisNasabahPenerimaCommand command, User authPrincipal) {
        // Find existing parameter
        ParameterJenisNasabahPenerima parameter = parameterJenisNasabahPenerimaRepository.findFirstByIdAndAuditDeletedAtIsNull(
                        new ParameterJenisNasabahPenerimaId(command.code()))
                .orElseThrow(() -> new ParameterJenisNasabahPenerimaNotFoundException(new ParameterJenisNasabahPenerimaId(command.code())));

        // Update fields
        parameter.changeValue(new ParameterJenisNasabahPenerimaValue(command.value()));

        // Update audit
        parameter.updateAudit(authPrincipal.getId().value());

        // Save to repository
        ParameterJenisNasabahPenerima saved = parameterJenisNasabahPenerimaRepository.save(parameter);

        // Convert to response DTO
        return ParameterJenisNasabahPenerimaResponse.builder()
                .code(saved.getCode().value())
                .value(saved.getValue().value())
                .build();
    }

}
