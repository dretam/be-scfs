package bank_mega.corsys.application.assembler;

import bank_mega.corsys.application.user.dto.UserResponse;
import bank_mega.corsys.domain.model.company.Company;
import bank_mega.corsys.domain.model.permission.Permission;
import bank_mega.corsys.domain.model.user.User;
import bank_mega.corsys.infrastructure.adapter.out.mapper.RoleMapper;
import bank_mega.corsys.infrastructure.config.security.PermissionEvaluator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserAssembler {

    private final PermissionEvaluator permissionEvaluator;

    public UserResponse toResponse(User saved) {
        return toResponse(saved, null);
    }

    public UserResponse toResponse(User saved, Set<String> expands) {
        if (saved == null) return null;
        UserResponse.UserResponseBuilder builder = UserResponse.builder()
                .id(saved.getId().value())
                .name(saved.getName().value())
                .fullName(saved.getFullName().value())
                .email(saved.getEmail().value())
                .isActive(saved.getIsActive().value())
                .photoPath(saved.getPhotoPath().value())
                .createdAt(saved.getAudit().createdAt())
                .createdBy(saved.getAudit().createdBy())
                .updatedAt(saved.getAudit().updatedAt())
                .updatedBy(saved.getAudit().updatedBy())
                .deletedAt(saved.getAudit().deletedAt())
                .deletedBy(saved.getAudit().deletedBy());

        if (expands == null || expands.contains("role")) {
            Set<Permission> effectivePermissions = (expands != null && expands.contains("permissions"))
                    ? permissionEvaluator.getEffectivePermissions(saved)
                    : null;

            builder = builder.role(RoleAssembler.toResponse(saved.getRole(), expands, effectivePermissions));
        }

        if (expands == null || expands.contains("company")) {
            builder = builder.company(CompanyAssembler.toResponse(saved.getCompany()));
        }

        if (expands != null && expands.contains("userPermission") && saved.getUserPermissionOverride() != null) {
            builder = builder.userPermissionOverride(saved.getUserPermissionOverride().stream().map(UserPermissionAssembler::toResponse).toList());
        }

        return builder.build();
    }

}
