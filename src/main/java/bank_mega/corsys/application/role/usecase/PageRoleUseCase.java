package bank_mega.corsys.application.role.usecase;

import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.domain.model.role.Role;
import bank_mega.corsys.domain.repository.RoleRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;


@UseCase
@RequiredArgsConstructor
public class PageRoleUseCase {

    private final RoleRepository roleRepository;

    @Transactional(readOnly = true)
    public Page<@NonNull Role> execute(int page, int size, String sort, String filter) {
        return roleRepository.findAllPageable(page, size, sort, filter);
    }

}
