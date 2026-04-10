package bank_mega.corsys.application.rolechildren.usecase;

import bank_mega.corsys.application.assembler.RoleAssembler;
import bank_mega.corsys.application.assembler.RoleChildrenAssembler;
import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.application.role.command.SoftDeleteRoleCommand;
import bank_mega.corsys.application.role.dto.RoleResponse;
import bank_mega.corsys.application.rolechildren.command.SoftDeleteRoleChildrenCommand;
import bank_mega.corsys.application.rolechildren.dto.RoleChildrenResponse;
import bank_mega.corsys.domain.exception.RoleChildrenNotFoundException;
import bank_mega.corsys.domain.exception.RoleNotFoundException;
import bank_mega.corsys.domain.model.role.Role;
import bank_mega.corsys.domain.model.role.RoleCode;
import bank_mega.corsys.domain.model.rolechildren.RoleChildren;
import bank_mega.corsys.domain.model.rolechildren.RoleChildrenCode;
import bank_mega.corsys.domain.model.user.User;
import bank_mega.corsys.domain.repository.RoleChildrenRepository;
import bank_mega.corsys.domain.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;


@UseCase
@RequiredArgsConstructor
public class SoftDeleteRoleChildrenUseCase {

    private final RoleRepository roleRepository;

    private final RoleChildrenRepository roleChildrenRepository;

    @Transactional
    public RoleChildrenResponse execute(SoftDeleteRoleChildrenCommand command, User authPrincipal) {

        // 1. Validasi bahwa role ada
        Role role = roleRepository.findFirstByIdAndAuditDeletedAtIsNull(new RoleCode(command.id())).orElseThrow(
                () -> new RoleNotFoundException(new RoleCode(command.id()))
        );

        RoleChildren roleChildren =
                roleChildrenRepository.findFirstByIdAndAuditDeletedAtIsNull(new RoleChildrenCode(command.parentId())).orElseThrow(
                () -> new RoleChildrenNotFoundException(new RoleChildrenCode(command.parentId()))
        );

        // 2. Update Soft Delete
        roleChildren.deleteAudit(authPrincipal.getId().value());

        // 3. Simpan di repository (masuk ke infra → mapper → jpa)
        RoleChildren saved = roleChildrenRepository.save(roleChildren);

        // 4. convert ke response DTO
        return RoleChildrenAssembler.toResponse(saved);
    }

}
