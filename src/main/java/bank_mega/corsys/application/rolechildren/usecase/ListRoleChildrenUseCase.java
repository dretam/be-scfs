package bank_mega.corsys.application.rolechildren.usecase;

import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.domain.model.role.Role;
import bank_mega.corsys.domain.model.rolechildren.RoleChildren;
import bank_mega.corsys.domain.repository.RoleChildrenRepository;
import bank_mega.corsys.domain.repository.RoleRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@UseCase
@RequiredArgsConstructor
public class ListRoleChildrenUseCase {
    private final RoleChildrenRepository roleChildrenRepository;

    @Transactional(readOnly = true)
    public List<@NonNull RoleChildren> execute() {
        return roleChildrenRepository.findAllByAuditDeletedAtIsNull();
    }
}
