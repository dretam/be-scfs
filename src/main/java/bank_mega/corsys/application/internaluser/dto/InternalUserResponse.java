package bank_mega.corsys.application.internaluser.dto;

import bank_mega.corsys.application.branch.dto.BranchResponse;
import lombok.Builder;

import java.time.LocalDate;
import java.time.Instant;
import java.time.LocalDateTime;

@Builder
public record InternalUserResponse(
        String userName,
        String nama,
        LocalDate joinDate,
        Integer jabatan,
        String approval1,
        String approval2,
        LocalDateTime lastLogin,
        LocalDateTime updatePass,
        Integer status,
        Integer count,
        String email,
        String area,
        String jobTitle,
        String direktorat,
        String sex,
        String employee,
        String mobile,
        String extOffice,
        String tglLahir,
        String pangkat,
        String sessionId,
        BranchResponse usersCabang,
        BranchResponse usersBranch
) {
}
