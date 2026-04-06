package bank_mega.corsys.application.role.usecase;

import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.domain.model.role.Role;
import bank_mega.corsys.domain.repository.RoleRepository;
import bank_mega.corsys.infrastructure.adapter.out.jpa.entity.RoleJpaEntity;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@UseCase
@RequiredArgsConstructor
public class ListRoleUseCase {
    private final RoleRepository roleRepository;

    @Transactional(readOnly = true)
    public List<@NonNull Role> execute() {
        return roleRepository.findAllByAuditDeletedAtIsNull();
    }
}
