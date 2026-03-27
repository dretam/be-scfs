package bank_mega.corsys.application.role.usecase;

import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.application.assembler.RoleAssembler;
import bank_mega.corsys.application.role.command.UpdateRoleCommand;
import bank_mega.corsys.application.role.dto.RoleResponse;
import bank_mega.corsys.domain.exception.RoleNotFoundException;
import bank_mega.corsys.domain.model.role.Role;
import bank_mega.corsys.domain.model.role.RoleCode;
import bank_mega.corsys.domain.model.role.RoleIcon;
import bank_mega.corsys.domain.model.role.RoleName;
import bank_mega.corsys.domain.model.user.User;
import bank_mega.corsys.domain.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;


@UseCase
@RequiredArgsConstructor
public class UpdateRoleUseCase {

    private final RoleRepository roleRepository;

    @Transactional
    public RoleResponse execute(UpdateRoleCommand command, User authPrincipal) {

        // 1. Validasi bahwa user ada
        Role role = roleRepository.findFirstByIdAndAuditDeletedAtIsNull(new RoleCode(command.id())).orElseThrow(
                () -> new RoleNotFoundException(new RoleCode(command.id()))
        );

        // 2. Transaksi
        command.name().ifPresent(name -> role.changeName(new RoleName(name)));

        command.description().ifPresent(role::changeDescription);

        command.icon().ifPresent(icon -> role.changeIcon(new RoleIcon(icon)));

        role.updateAudit(authPrincipal.getId().value());

        // 3. simpan di repository (masuk ke infra → mapper → jpa)
        Role saved = roleRepository.save(role);

        // 4. convert ke response DTO
        return RoleAssembler.toResponse(saved);
    }

}
