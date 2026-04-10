package bank_mega.corsys.application.rolechildren.usecase;

import bank_mega.corsys.application.assembler.RoleAssembler;
import bank_mega.corsys.application.assembler.RoleChildrenAssembler;
import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.application.role.dto.RoleResponse;
import bank_mega.corsys.application.rolechildren.dto.RoleChildrenResponse;
import bank_mega.corsys.domain.exception.RoleChildrenNotFoundException;
import bank_mega.corsys.domain.exception.RoleNotFoundException;
import bank_mega.corsys.domain.model.role.Role;
import bank_mega.corsys.domain.model.role.RoleCode;
import bank_mega.corsys.domain.model.rolechildren.RoleChildren;
import bank_mega.corsys.domain.model.rolechildren.RoleChildrenCode;
import bank_mega.corsys.domain.repository.RoleChildrenRepository;
import bank_mega.corsys.domain.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@UseCase
@RequiredArgsConstructor
public class RetrieveRoleChildrenUseCase {

    private final RoleRepository roleRepository;

    private final RoleChildrenRepository roleChildrenRepository;

    @Transactional(readOnly = true)
    public RoleChildrenResponse execute(String id, String parentId) {
        return execute(id, parentId, null);
    }

    @Transactional(readOnly = true)
    public RoleChildrenResponse execute(String id, String parentId, Set<String> expands) {
        // Validate that role exists
        Role role = roleRepository.findFirstByIdAndAuditDeletedAtIsNull(new RoleCode(parentId), expands).orElseThrow(
                () -> new RoleNotFoundException(new RoleCode(id))
        );

        RoleChildren roleChildren =
                roleChildrenRepository.findFirstByIdAndAuditDeletedAtIsNull(new RoleChildrenCode(id), expands).orElseThrow(
                () -> new RoleChildrenNotFoundException(new RoleChildrenCode(id))
        );

        // Convert to response DTO
        return RoleChildrenAssembler.toResponse(roleChildren, expands);
    }

}
