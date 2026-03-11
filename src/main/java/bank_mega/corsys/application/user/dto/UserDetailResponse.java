package bank_mega.corsys.application.user.dto;

import bank_mega.corsys.application.branch.dto.BranchResponse;
import lombok.Builder;

import java.time.Instant;

@Builder
public record UserDetailResponse(
        Long id,
        Long userId,
        String nama,
        Integer jabatan,
        String email,
        String area,
        String jobTitle,
        String direktorat,
        String sex,
        String mobile,
        String tglLahir,
        BranchResponse usersCabang,
        BranchResponse usersBranch,
        Instant createdAt,
        Long createdBy,
        Instant updatedAt,
        Long updatedBy,
        Instant deletedAt,
        Long deletedBy
) {
}
