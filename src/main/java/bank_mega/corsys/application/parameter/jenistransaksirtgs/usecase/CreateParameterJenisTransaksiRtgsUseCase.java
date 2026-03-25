package bank_mega.corsys.application.parameter.jenistransaksirtgs.usecase;

import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.application.parameter.jenistransaksirtgs.command.CreateParameterJenisTransaksiRtgsCommand;
import bank_mega.corsys.application.parameter.jenistransaksirtgs.dto.ParameterJenisTransaksiRtgsResponse;
import bank_mega.corsys.domain.exception.DomainRuleViolationException;
import bank_mega.corsys.domain.model.common.AuditTrail;
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
public class CreateParameterJenisTransaksiRtgsUseCase {

    private final ParameterJenisTransaksiRtgsRepository parameterJenisTransaksiRtgsRepository;

    @Transactional
    public ParameterJenisTransaksiRtgsResponse execute(CreateParameterJenisTransaksiRtgsCommand command, User authPrincipal) {
        // Validate code uniqueness
        parameterJenisTransaksiRtgsRepository.findFirstByCode(new ParameterJenisTransaksiRtgsCode(command.code()))
                .ifPresent(existing -> {
                    throw new DomainRuleViolationException("ParameterJenisTransaksiRtgs code already exists: " + command.code());
                });

        // Create new parameter
        ParameterJenisTransaksiRtgs newParameter = new ParameterJenisTransaksiRtgs(
                new ParameterJenisTransaksiRtgsId(command.code()),
                new ParameterJenisTransaksiRtgsCode(command.code()),
                new ParameterJenisTransaksiRtgsValue(command.value()),
                AuditTrail.create(authPrincipal.getId().value())
        );

        // Save to repository
        ParameterJenisTransaksiRtgs saved = parameterJenisTransaksiRtgsRepository.save(newParameter);

        // Convert to response DTO
        return ParameterJenisTransaksiRtgsResponse.builder()
                .code(saved.getCode().value())
                .value(saved.getValue().value())
                .build();
    }

}
