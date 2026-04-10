package bank_mega.corsys.application.rolechildren.usecase;

import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.domain.model.role.Role;
import bank_mega.corsys.domain.model.rolechildren.RoleChildren;
import bank_mega.corsys.domain.repository.RoleChildrenRepository;
import bank_mega.corsys.domain.repository.RoleRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@UseCase
@RequiredArgsConstructor
public class PageRoleChildrenUseCase {

    private final RoleChildrenRepository roleChildrenRepository;

    @Transactional(readOnly = true)
    public Page<@NonNull RoleChildren> execute(int page, int size, Set<String> expands, String sort, String filter) {
        return roleChildrenRepository.findAllPageable(page, size, expands, sort, filter);
    }

}
