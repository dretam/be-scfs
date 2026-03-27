package bank_mega.corsys.application.role.usecase;

import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.domain.exception.RoleNotFoundException;
import bank_mega.corsys.domain.model.role.Role;
import bank_mega.corsys.domain.model.role.RoleCode;
import bank_mega.corsys.domain.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;


@UseCase
@RequiredArgsConstructor
public class DeleteRoleUseCase {

    private final RoleRepository roleRepository;

    @Transactional
    public RoleCode execute(String id) {
        // 1. Validasi bahwa role ada
        Role role = roleRepository.findFirstById(new RoleCode(id)).orElseThrow(
                () -> new RoleNotFoundException(new RoleCode(id))
        );

        // 2. Simpan di repository (masuk ke infra → mapper → jpa)
        roleRepository.delete(role);

        // 3. convert ke response DTO
        return new RoleCode(id);
    }

}
