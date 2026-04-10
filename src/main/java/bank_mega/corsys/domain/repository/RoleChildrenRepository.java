package bank_mega.corsys.domain.repository;

import bank_mega.corsys.domain.model.role.Role;
import bank_mega.corsys.domain.model.role.RoleCode;
import bank_mega.corsys.domain.model.role.RoleName;
import bank_mega.corsys.domain.model.rolechildren.RoleChildren;
import bank_mega.corsys.domain.model.rolechildren.RoleChildrenCode;
import bank_mega.corsys.domain.model.rolechildren.RoleChildrenName;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
@Repository
public interface RoleChildrenRepository {

    RoleChildren save(RoleChildren roleChildren);

    void delete(RoleChildren roleChildren);

    long count();

    Page<@NonNull RoleChildren> findAllPageable(int page, int size, Set<String> expands, String sort, String filter);

    List<@NonNull RoleChildren> findAllByAuditDeletedAtIsNull();

    Optional<RoleChildren> findFirstById(RoleChildrenCode id);

    Optional<RoleChildren> findFirstByIdAndAuditDeletedAtIsNull(RoleChildrenCode id);

    Optional<RoleChildren> findFirstByIdAndAuditDeletedAtIsNull(RoleChildrenCode id, Set<String> expands);

    Optional<RoleChildren> findFirstByName(RoleChildrenName name);

}
