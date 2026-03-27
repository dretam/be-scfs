package bank_mega.corsys.application.role.usecase;

import bank_mega.corsys.application.assembler.RoleAssembler;
import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.application.role.dto.RoleResponse;
import bank_mega.corsys.domain.exception.RoleNotFoundException;
import bank_mega.corsys.domain.model.role.Role;
import bank_mega.corsys.domain.model.role.RoleCode;
import bank_mega.corsys.domain.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@UseCase
@RequiredArgsConstructor
public class RetrieveRoleUseCase {

    private final RoleRepository roleRepository;

    @Transactional(readOnly = true)
    public RoleResponse execute(String id) {
        return execute(id, null);
    }

    @Transactional(readOnly = true)
    public RoleResponse execute(String id, Set<String> expands) {
        // Validate that role exists
        Role role = roleRepository.findFirstByIdAndAuditDeletedAtIsNull(new RoleCode(id), expands).orElseThrow(
                () -> new RoleNotFoundException(new RoleCode(id))
        );

        // Convert to response DTO
        return RoleAssembler.toResponse(role, expands);
    }

}
