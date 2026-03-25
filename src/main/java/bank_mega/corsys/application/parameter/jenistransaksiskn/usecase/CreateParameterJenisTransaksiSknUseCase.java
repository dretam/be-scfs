package bank_mega.corsys.application.parameter.jenistransaksiskn.usecase;

import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.application.parameter.jenistransaksiskn.command.CreateParameterJenisTransaksiSknCommand;
import bank_mega.corsys.application.parameter.jenistransaksiskn.dto.ParameterJenisTransaksiSknResponse;
import bank_mega.corsys.domain.exception.DomainRuleViolationException;
import bank_mega.corsys.domain.model.common.AuditTrail;
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
public class CreateParameterJenisTransaksiSknUseCase {

    private final ParameterJenisTransaksiSknRepository parameterJenisTransaksiSknRepository;

    @Transactional
    public ParameterJenisTransaksiSknResponse execute(CreateParameterJenisTransaksiSknCommand command, User authPrincipal) {
        // Validate code uniqueness
        parameterJenisTransaksiSknRepository.findFirstByCode(new ParameterJenisTransaksiSknCode(command.code()))
                .ifPresent(existing -> {
                    throw new DomainRuleViolationException("ParameterJenisTransaksiSkn code already exists: " + command.code());
                });

        // Create new parameter
        ParameterJenisTransaksiSkn newParameter = new ParameterJenisTransaksiSkn(
                new ParameterJenisTransaksiSknId(command.code()),
                new ParameterJenisTransaksiSknCode(command.code()),
                new ParameterJenisTransaksiSknValue(command.value()),
                AuditTrail.create(authPrincipal.getId().value())
        );

        // Save to repository
        ParameterJenisTransaksiSkn saved = parameterJenisTransaksiSknRepository.save(newParameter);

        // Convert to response DTO
        return ParameterJenisTransaksiSknResponse.builder()
                .code(saved.getCode().value())
                .value(saved.getValue().value())
                .build();
    }

}
