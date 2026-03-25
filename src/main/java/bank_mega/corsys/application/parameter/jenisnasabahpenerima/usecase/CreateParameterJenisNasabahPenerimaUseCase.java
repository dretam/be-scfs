package bank_mega.corsys.application.parameter.jenisnasabahpenerima.usecase;

import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.application.parameter.jenisnasabahpenerima.command.CreateParameterJenisNasabahPenerimaCommand;
import bank_mega.corsys.application.parameter.jenisnasabahpenerima.dto.ParameterJenisNasabahPenerimaResponse;
import bank_mega.corsys.domain.exception.DomainRuleViolationException;
import bank_mega.corsys.domain.model.common.AuditTrail;
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
public class CreateParameterJenisNasabahPenerimaUseCase {

    private final ParameterJenisNasabahPenerimaRepository parameterJenisNasabahPenerimaRepository;

    @Transactional
    public ParameterJenisNasabahPenerimaResponse execute(CreateParameterJenisNasabahPenerimaCommand command, User authPrincipal) {
        // Validate code uniqueness
        parameterJenisNasabahPenerimaRepository.findFirstByCode(new ParameterJenisNasabahPenerimaCode(command.code()))
                .ifPresent(existing -> {
                    throw new DomainRuleViolationException("ParameterJenisNasabahPenerima code already exists: " + command.code());
                });

        // Create new parameter
        ParameterJenisNasabahPenerima newParameter = new ParameterJenisNasabahPenerima(
                new ParameterJenisNasabahPenerimaId(command.code()),
                new ParameterJenisNasabahPenerimaCode(command.code()),
                new ParameterJenisNasabahPenerimaValue(command.value()),
                AuditTrail.create(authPrincipal.getId().value())
        );

        // Save to repository
        ParameterJenisNasabahPenerima saved = parameterJenisNasabahPenerimaRepository.save(newParameter);

        // Convert to response DTO
        return ParameterJenisNasabahPenerimaResponse.builder()
                .code(saved.getCode().value())
                .value(saved.getValue().value())
                .build();
    }

}
