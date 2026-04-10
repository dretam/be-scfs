package bank_mega.corsys.application.rolechildren.usecase;

import bank_mega.corsys.application.assembler.RoleChildrenAssembler;
import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.application.rolechildren.command.CreateRoleChildrenCommand;
import bank_mega.corsys.application.rolechildren.dto.RoleChildrenResponse;
import bank_mega.corsys.domain.exception.RoleNotFoundException;
import bank_mega.corsys.domain.model.common.AuditTrail;
import bank_mega.corsys.domain.model.role.Role;
import bank_mega.corsys.domain.model.role.RoleCode;
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
public class CreateRoleChildrenUseCase {

    private final RoleRepository roleRepository;

    private final RoleChildrenRepository roleChildrenRepository;

    @Transactional
    public RoleChildrenResponse execute(CreateRoleChildrenCommand command, User authPrincipal) {
        Role role = roleRepository.findFirstByIdAndAuditDeletedAtIsNull(
            new RoleCode(
                command.parentCode()
            )
        ).orElseThrow(() -> new RoleNotFoundException(new RoleCode(command.parentCode()))
        );

        // 1. set domain role
        RoleChildren newRoleChildren = new RoleChildren(
                new RoleChildrenName(command.name()),
                new RoleChildrenCode(command.code()),
                new RoleChildrenIcon(command.icon()),
                role,
                command.description(),
                AuditTrail.create(authPrincipal.getId().value())
        );

        // 2. simpan di repository (masuk ke infra → mapper → jpa)
        RoleChildren saved = roleChildrenRepository.save(newRoleChildren);

        // 3. convert ke response DTO
        return RoleChildrenAssembler.toResponse(saved);
    }

}
