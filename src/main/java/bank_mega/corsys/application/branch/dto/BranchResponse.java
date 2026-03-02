package bank_mega.corsys.application.branch.dto;

import lombok.Builder;

import java.time.Instant;

@Builder
public record BranchResponse(
        Long id,
        String idBranch,
        String branchName,
        Boolean flagDel,
        String category,
        String regional,
        String address,
        String area,
        String direktorat,
        Long modId,
        Long telepon,
        Long faximile,
        String singkatan,
        Instant createdAt,
        Long createdBy,
        Instant updatedAt,
        Long updatedBy,
        Instant deletedAt,
        Long deletedBy
) {
}
