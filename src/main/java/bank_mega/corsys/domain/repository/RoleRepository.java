package bank_mega.corsys.domain.repository;

import bank_mega.corsys.domain.model.role.Role;
import bank_mega.corsys.domain.model.role.RoleId;
import bank_mega.corsys.domain.model.role.RoleName;
import lombok.NonNull;
import org.springframework.data.domain.Page;

import java.util.Optional;
import java.util.Set;

public interface RoleRepository {

    Role save(Role role);

    void delete(Role role);

    long count();

    Page<@NonNull Role> findAllPageable(int page, int size, Set<String> expands, String sort, String filter);

    Optional<Role> findFirstById(RoleId id);

    Optional<Role> findFirstByIdAndAuditDeletedAtIsNull(RoleId id);

    Optional<Role> findFirstByIdAndAuditDeletedAtIsNull(RoleId id, Set<String> expands);

    Optional<Role> findFirstByName(RoleName name);

}
