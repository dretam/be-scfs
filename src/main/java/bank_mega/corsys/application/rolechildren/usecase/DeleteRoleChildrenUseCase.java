package bank_mega.corsys.application.rolechildren.usecase;

import bank_mega.corsys.application.common.annotation.UseCase;
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


@UseCase
@RequiredArgsConstructor
public class DeleteRoleChildrenUseCase {

    private final RoleChildrenRepository roleChildrenRepository;

    @Transactional
    public RoleChildrenCode execute(String id) {
        // 1. Validasi bahwa role ada
        RoleChildren roleChildren = roleChildrenRepository.findFirstById(new RoleChildrenCode(id)).orElseThrow(
                () -> new RoleChildrenNotFoundException(new RoleChildrenCode(id))
        );

        // 2. Simpan di repository (masuk ke infra → mapper → jpa)
        roleChildrenRepository.delete(roleChildren);

        // 3. convert ke response DTO
        return new RoleChildrenCode(id);
    }

}
