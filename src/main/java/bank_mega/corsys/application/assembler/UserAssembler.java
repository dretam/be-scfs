package bank_mega.corsys.application.assembler;

import bank_mega.corsys.application.user.dto.UserDetailResponse;
import bank_mega.corsys.application.user.dto.UserResponse;
import bank_mega.corsys.domain.model.permission.Permission;
import bank_mega.corsys.domain.model.user.User;
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
                .email(saved.getEmail().value())
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

        if (expands != null && expands.contains("userDetail") && saved.getUserDetail() != null) {
            builder = builder.userDetail(toUserDetailResponse(saved.getUserDetail(), expands));
        }

        return builder.build();
    }

    private UserDetailResponse toUserDetailResponse(bank_mega.corsys.domain.model.userdetail.UserDetail userDetail, Set<String> expands) {
        if (userDetail == null) return null;

        UserDetailResponse.UserDetailResponseBuilder builder = UserDetailResponse.builder()
                .id(userDetail.getId() != null ? userDetail.getId().value() : null)
                .userId(userDetail.getUserId().value())
                .nama(userDetail.getNama())
                .jabatan(userDetail.getJabatan() != null ? userDetail.getJabatan().value() : null)
                .email(userDetail.getEmail() != null ? userDetail.getEmail().value() : null)
                .area(userDetail.getArea() != null ? userDetail.getArea().value() : null)
                .jobTitle(userDetail.getJobTitle())
                .direktorat(userDetail.getDirektorat() != null ? userDetail.getDirektorat().value() : null)
                .sex(userDetail.getSex())
                .mobile(userDetail.getMobile())
                .tglLahir(userDetail.getTglLahir())
                .createdAt(userDetail.getAudit().createdAt())
                .createdBy(userDetail.getAudit().createdBy())
                .updatedAt(userDetail.getAudit().updatedAt())
                .updatedBy(userDetail.getAudit().updatedBy())
                .deletedAt(userDetail.getAudit().deletedAt())
                .deletedBy(userDetail.getAudit().deletedBy());

        if (expands != null && expands.contains("usersCabang") && userDetail.getUsersCabang() != null) {
            builder = builder.usersCabang(bank_mega.corsys.application.assembler.BranchAssembler.toResponse(userDetail.getUsersCabang()));
        }

        if (expands != null && expands.contains("usersBranch") && userDetail.getUsersBranch() != null) {
            builder = builder.usersBranch(bank_mega.corsys.application.assembler.BranchAssembler.toResponse(userDetail.getUsersBranch()));
        }

        return builder.build();
    }

}
