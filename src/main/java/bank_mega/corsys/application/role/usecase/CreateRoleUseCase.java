package bank_mega.corsys.application.role.usecase;

import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.application.assembler.RoleAssembler;
import bank_mega.corsys.application.role.command.CreateRoleCommand;
import bank_mega.corsys.application.role.dto.RoleResponse;
import bank_mega.corsys.domain.model.common.AuditTrail;
import bank_mega.corsys.domain.model.role.Role;
import bank_mega.corsys.domain.model.role.RoleIcon;
import bank_mega.corsys.domain.model.role.RoleName;
import bank_mega.corsys.domain.model.user.User;
import bank_mega.corsys.domain.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;


@UseCase
@RequiredArgsConstructor
public class CreateRoleUseCase {

    private final RoleRepository roleRepository;

    @Transactional
    public RoleResponse execute(CreateRoleCommand command, User authPrincipal) {
        // 1. set domain role
        Role newRole = new Role(
                null,
                new RoleName(command.name()),
                new RoleIcon(command.icon()),
                command.description(),
                AuditTrail.create(authPrincipal.getId().value())
        );

        // 2. simpan di repository (masuk ke infra → mapper → jpa)
        Role saved = roleRepository.save(newRole);

        // 3. convert ke response DTO
        return RoleAssembler.toResponse(saved);
    }

}
