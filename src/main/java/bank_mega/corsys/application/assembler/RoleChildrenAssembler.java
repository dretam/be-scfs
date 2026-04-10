package bank_mega.corsys.application.assembler;

import bank_mega.corsys.application.permission.dto.PermissionResponse;
import bank_mega.corsys.application.role.dto.RoleResponse;
import bank_mega.corsys.application.rolechildren.dto.RoleChildrenResponse;
import bank_mega.corsys.domain.model.permission.Permission;
import bank_mega.corsys.domain.model.role.Role;
import bank_mega.corsys.domain.model.rolechildren.RoleChildren;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@Slf4j
public class RoleChildrenAssembler {

    public static RoleChildrenResponse toResponse(RoleChildren roleChildren) {
        return toResponse(roleChildren, null);
    }

    public static RoleChildrenResponse toResponse(RoleChildren roleChildren, Set<String> expands) {
        return toResponse(roleChildren, expands, null);
    }

    public static RoleChildrenResponse toResponse(RoleChildren roleChildren, Set<String> expands,
                                          Set<Permission> effectivePermissions) {
        if (roleChildren == null) return null;

        boolean includePermissions = expands == null || expands.contains("permissions");
        boolean includeMenus = expands == null || expands.contains("menus");

        return RoleChildrenResponse.builder()
                .id(roleChildren.getCode().value())
                .name(roleChildren.getName().value())
                .icon(roleChildren.getIcon().value())
                .description(roleChildren.getDescription())
                .createdAt(roleChildren.getAudit().createdAt())
                .createdBy(roleChildren.getAudit().createdBy())
                .updatedAt(roleChildren.getAudit().updatedAt())
                .updatedBy(roleChildren.getAudit().updatedBy())
                .deletedAt(roleChildren.getAudit().deletedAt())
                .deletedBy(roleChildren.getAudit().deletedBy())
                .permissions(includePermissions
                        ? getPermissionsToShow(roleChildren, effectivePermissions)
                        : Collections.emptyList())
                .menus(includeMenus && roleChildren.getMenus() != null
                        ? roleChildren.getMenus().stream()
                        .map(MenuAssembler::toResponse)
                        .toList()
                        : Collections.emptyList())
                .build();
    }

    private static List<PermissionResponse> getPermissionsToShow(RoleChildren roleChildren,
                                                                 Set<Permission> effectivePermissions) {
        if (effectivePermissions != null) {
            return effectivePermissions.stream()
                    .map(PermissionAssembler::toResponse)
                    .toList();
        }

        if (roleChildren.getPermissions() != null) {
            return roleChildren.getPermissions().stream()
                    .map(PermissionAssembler::toResponse)
                    .toList();
        }

        return Collections.emptyList();
    }

}
