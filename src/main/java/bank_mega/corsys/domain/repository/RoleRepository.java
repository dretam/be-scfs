package bank_mega.corsys.domain.repository;

import bank_mega.corsys.domain.model.role.Role;
import bank_mega.corsys.domain.model.role.RoleCode;
import bank_mega.corsys.domain.model.role.RoleName;
import lombok.NonNull;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface RoleRepository {

    Role save(Role role);

    void delete(Role role);

    long count();

    Page<@NonNull Role> findAllPageable(int page, int size, Set<String> expands, String sort, String filter);

    List<@NonNull Role> findAllByAuditDeletedAtIsNull();

    Optional<Role> findFirstById(RoleCode id);

    Optional<Role> findFirstByIdAndAuditDeletedAtIsNull(RoleCode id);

    Optional<Role> findFirstByIdAndAuditDeletedAtIsNull(RoleCode id, Set<String> expands);

    Optional<Role> findFirstByName(RoleName name);

}
