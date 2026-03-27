package bank_mega.corsys.application.role.usecase;

import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.application.assembler.RoleAssembler;
import bank_mega.corsys.application.role.command.SoftDeleteRoleCommand;
import bank_mega.corsys.application.role.dto.RoleResponse;
import bank_mega.corsys.domain.exception.RoleNotFoundException;
import bank_mega.corsys.domain.model.role.*;
import bank_mega.corsys.domain.model.user.User;
import bank_mega.corsys.domain.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;


@UseCase
@RequiredArgsConstructor
public class SoftDeleteRoleUseCase {

    private final RoleRepository roleRepository;

    @Transactional
    public RoleResponse execute(SoftDeleteRoleCommand command, User authPrincipal) {

        // 1. Validasi bahwa role ada
        Role role = roleRepository.findFirstByIdAndAuditDeletedAtIsNull(new RoleCode(command.id())).orElseThrow(
                () -> new RoleNotFoundException(new RoleCode(command.id()))
        );

        // 2. Update Soft Delete
        role.deleteAudit(authPrincipal.getId().value());

        // 3. Simpan di repository (masuk ke infra → mapper → jpa)
        Role saved = roleRepository.save(role);

        // 4. convert ke response DTO
        return RoleAssembler.toResponse(saved);
    }

}
