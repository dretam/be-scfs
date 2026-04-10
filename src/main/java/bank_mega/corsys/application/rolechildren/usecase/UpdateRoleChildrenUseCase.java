package bank_mega.corsys.application.rolechildren.usecase;

import bank_mega.corsys.application.assembler.RoleAssembler;
import bank_mega.corsys.application.assembler.RoleChildrenAssembler;
import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.application.role.command.UpdateRoleCommand;
import bank_mega.corsys.application.role.dto.RoleResponse;
import bank_mega.corsys.application.rolechildren.command.UpdateRoleChildrenCommand;
import bank_mega.corsys.application.rolechildren.dto.RoleChildrenResponse;
import bank_mega.corsys.domain.exception.RoleChildrenNotFoundException;
import bank_mega.corsys.domain.exception.RoleNotFoundException;
import bank_mega.corsys.domain.model.role.Role;
import bank_mega.corsys.domain.model.role.RoleCode;
import bank_mega.corsys.domain.model.role.RoleIcon;
import bank_mega.corsys.domain.model.role.RoleName;
import bank_mega.corsys.domain.model.rolechildren.RoleChildren;
import bank_mega.corsys.domain.model.rolechildren.RoleChildrenCode;
import bank_mega.corsys.domain.model.rolechildren.RoleChildrenIcon;
import bank_mega.corsys.domain.model.rolechildren.RoleChildrenName;
import bank_mega.corsys.domain.model.user.User;
import bank_mega.corsys.domain.repository.RoleChildrenRepository;
import bank_mega.corsys.domain.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;


@UseCase
@RequiredArgsConstructor
public class UpdateRoleChildrenUseCase {

    private final RoleRepository roleRepository;

    private final RoleChildrenRepository roleChildrenRepository;

    @Transactional
    public RoleChildrenResponse execute(UpdateRoleChildrenCommand command, User authPrincipal) {

        // 1. Validasi bahwa user ada
        Role role = roleRepository.findFirstByIdAndAuditDeletedAtIsNull(
                new RoleCode(
                        command.parentId()
                )
        ).orElseThrow(() -> new RoleNotFoundException(new RoleCode(command.parentId()))
        );

        RoleChildren roleChildren =
                roleChildrenRepository.findFirstByIdAndAuditDeletedAtIsNull(new RoleChildrenCode(command.id())).orElseThrow(
                () -> new RoleChildrenNotFoundException(new RoleChildrenCode(command.id()))
        );

        // 2. Transaksi
        command.name().ifPresent(name -> roleChildren.changeName(new RoleChildrenName(name)));

        command.description().ifPresent(roleChildren::changeDescription);

        command.icon().ifPresent(icon -> roleChildren.changeIcon(new RoleChildrenIcon(icon)));

        roleChildren.addRole(role);

        roleChildren.updateAudit(authPrincipal.getId().value());

        // 3. simpan di repository (masuk ke infra → mapper → jpa)
        RoleChildren saved = roleChildrenRepository.save(roleChildren);

        // 4. convert ke response DTO
        return RoleChildrenAssembler.toResponse(saved);
    }

}
