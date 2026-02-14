package bank_mega.corsys.application.assembler;

import bank_mega.corsys.application.role.dto.RoleResponse;
import bank_mega.corsys.domain.model.role.Role;

public class RoleAssembler {

    public static RoleResponse toResponse(Role saved) {
        if (saved == null) return null;
        return RoleResponse.builder()
                .id(saved.getId().value())
                .name(saved.getName().value())
                .icon(saved.getIcon().value())
                .description(saved.getDescription())
                .createdAt(saved.getAudit().createdAt())
                .createdBy(saved.getAudit().createdBy())
                .updatedAt(saved.getAudit().updatedAt())
                .updatedBy(saved.getAudit().updatedBy())
                .deletedAt(saved.getAudit().deletedAt())
                .deletedBy(saved.getAudit().deletedBy())
                .build();
    }

}
