package bank_mega.corsys.application.assembler;

import bank_mega.corsys.application.menu.dto.MenuResponse;
import bank_mega.corsys.application.permission.dto.PermissionResponse;
import bank_mega.corsys.application.role.dto.RoleResponse;
import bank_mega.corsys.domain.model.permission.Permission;
import bank_mega.corsys.domain.model.role.Role;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@Slf4j
public class RoleAssembler {

    public static RoleResponse toResponse(Role role) {
        return toResponse(role, null);
    }

    public static RoleResponse toResponse(Role role, Set<String> expands) {
        return toResponse(role, expands, null);
    }

    public static RoleResponse toResponse(Role role, Set<String> expands, Set<Permission> effectivePermissions) {
        if (role == null) return null;

        // Check if we should include permissions and menus
        boolean includePermissions = expands == null || expands.contains("permissions");
        boolean includeMenus = expands == null || expands.contains("menus");

        return RoleResponse.builder()
                .id(role.getId().value())
                .name(role.getName().value())
                .icon(role.getIcon().value())
                .description(role.getDescription())
                .createdAt(role.getAudit().createdAt())
                .createdBy(role.getAudit().createdBy())
                .updatedAt(role.getAudit().updatedAt())
                .updatedBy(role.getAudit().updatedBy())
                .deletedAt(role.getAudit().deletedAt())
                .deletedBy(role.getAudit().deletedBy())
                .permissions(includePermissions && role.getPermissions() != null && effectivePermissions != null
                        ? effectivePermissions.stream().map(PermissionAssembler::toResponse).toList()
                        : Collections.emptyList())
                .menus(includeMenus && role.getMenus() != null
                        ? role.getMenus().stream()
                                .map(MenuAssembler::toResponse)
                                .toList()
                        : Collections.emptyList())
                .build();
    }

}
