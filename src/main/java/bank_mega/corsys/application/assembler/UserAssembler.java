package bank_mega.corsys.application.assembler;

import bank_mega.corsys.application.user.dto.UserResponse;
import bank_mega.corsys.domain.model.user.User;

import java.util.Set;

public class UserAssembler {

    public static UserResponse toResponse(User saved) {
        if (saved == null) return null;
        return UserResponse.builder()
                .id(saved.getId().value())
                .name(saved.getName().value())
                .email(saved.getEmail().value())
                .role(RoleAssembler.toResponse(saved.getRole()))
                .createdAt(saved.getAudit().createdAt())
                .createdBy(saved.getAudit().createdBy())
                .updatedAt(saved.getAudit().updatedAt())
                .updatedBy(saved.getAudit().updatedBy())
                .deletedAt(saved.getAudit().deletedAt())
                .deletedBy(saved.getAudit().deletedBy())
                .build();
    }

    public static UserResponse toResponse(User saved, Set<String> expands) {
        if (saved == null) return null;
        UserResponse.UserResponseBuilder builder = UserResponse.builder()
                .id(saved.getId().value())
                .name(saved.getName().value())
                .email(saved.getEmail().value())
                .createdAt(saved.getAudit().createdAt())
                .createdBy(saved.getAudit().createdBy())
                .updatedAt(saved.getAudit().updatedAt())
                .updatedBy(saved.getAudit().updatedBy())
                .deletedAt(saved.getAudit().deletedAt())
                .deletedBy(saved.getAudit().deletedBy());
        if (expands.contains("role")) {
            builder = builder.role(RoleAssembler.toResponse(saved.getRole()));
        }
        return builder.build();
    }

}
