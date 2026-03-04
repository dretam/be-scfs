package bank_mega.corsys.application.assembler;

import bank_mega.corsys.application.permission.dto.PermissionResponse;
import bank_mega.corsys.domain.model.permission.Permission;

public class PermissionAssembler {

    public static PermissionResponse toResponse(Permission permission) {
        if (permission == null) return null;
        return PermissionResponse.builder()
                .id(permission.getId().value())
                .name(permission.getName().value())
                .code(permission.getCode().value())
                .description(permission.getDescription())
                .build();
    }

}
