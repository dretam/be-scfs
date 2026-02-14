package bank_mega.corsys.application.role.usecase;

import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.application.assembler.RoleAssembler;
import bank_mega.corsys.application.role.dto.RoleResponse;
import bank_mega.corsys.domain.exception.RoleNotFoundException;
import bank_mega.corsys.domain.model.role.Role;
import bank_mega.corsys.domain.model.role.RoleId;
import bank_mega.corsys.domain.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;


@UseCase
@RequiredArgsConstructor
public class RetrieveRoleUseCase {

    private final RoleRepository roleRepository;

    @Transactional(readOnly = true)
    public RoleResponse execute(Long id) {
        // 1. Validasi bahwa role ada
        Role role = roleRepository.findFirstByIdAndAuditDeletedAtIsNull(new RoleId(id)).orElseThrow(
                () -> new RoleNotFoundException(new RoleId(id))
        );

        // 2. convert ke response DTO
        return RoleAssembler.toResponse(role);
    }

}
