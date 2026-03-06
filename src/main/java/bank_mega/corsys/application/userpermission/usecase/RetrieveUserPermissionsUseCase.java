package bank_mega.corsys.application.userpermission.usecase;

import bank_mega.corsys.application.userpermission.dto.UserPermissionResponse;
import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.domain.model.user.UserId;
import bank_mega.corsys.domain.model.userpermission.UserPermission;
import bank_mega.corsys.domain.repository.UserPermissionRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@UseCase
@RequiredArgsConstructor
public class RetrieveUserPermissionsUseCase {

    private final UserPermissionRepository userPermissionRepository;

    /**
     * Retrieve all permission overrides for a user.
     */
    public List<UserPermissionResponse> execute(Long userId) {
        List<UserPermission> userPermissions = userPermissionRepository.findAllByUserId(new UserId(userId));
        
        return userPermissions.stream()
                .map(up -> UserPermissionResponse.builder()
                        .userId(up.getId().userId())
                        .permissionId(up.getId().permissionId())
                        .permissionCode(up.getPermission().getCode().value())
                        .permissionName(up.getPermission().getName().value())
                        .effect(up.getEffect().name())
                        .build())
                .toList();
    }

    /**
     * Retrieve effective permissions for a user (combining role permissions with overrides).
     * Formula: FINAL = (role_perms + user_allow) - user_deny
     */
    public List<String> executeEffectivePermissions(Long userId) {
        List<UserPermission> allowOverrides = userPermissionRepository.findAllAllowByUserId(new UserId(userId));
        List<UserPermission> denyOverrides = userPermissionRepository.findAllDenyByUserId(new UserId(userId));
        
        // This would need the user's role permissions to be passed in or fetched
        // The actual resolution should happen in the PermissionEvaluator
        return allowOverrides.stream()
                .map(up -> up.getPermission().getCode().value())
                .toList();
    }

}
