package bank_mega.corsys.domain.repository;

import bank_mega.corsys.domain.model.permission.Permission;
import bank_mega.corsys.domain.model.permission.PermissionCode;
import bank_mega.corsys.domain.model.permission.PermissionId;
import bank_mega.corsys.domain.model.role.RoleCode;
import lombok.NonNull;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface PermissionRepository {

    Permission save(Permission permission);

    void delete(Permission permission);

    long count();

    Page<@NonNull Permission> findAllPageable(int page, int size, String sort, String filter);

    Optional<Permission> findFirstById(PermissionId id);

    Optional<Permission> findFirstByIdAndAuditDeletedAtIsNull(PermissionId id);

    Optional<Permission> findFirstByCode(PermissionCode code);

    List<Permission> findAllByRoleId(RoleCode roleCode);

}
