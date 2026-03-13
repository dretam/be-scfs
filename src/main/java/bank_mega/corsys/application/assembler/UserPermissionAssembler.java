package bank_mega.corsys.application.assembler;

import bank_mega.corsys.application.userpermission.dto.UserPermissionResponse;
import bank_mega.corsys.domain.model.userpermission.UserPermission;


public class UserPermissionAssembler {
    public static UserPermissionResponse toResponse(UserPermission userPermission) {
        if (userPermission == null) return null;

        return UserPermissionResponse.builder()
                .userId(userPermission.getId().userId())
                .permissionId(userPermission.getId().permissionId())
                .effect(userPermission.getEffect().name())
                .build();
    }
}
