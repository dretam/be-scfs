package bank_mega.corsys.application.assembler;

import bank_mega.corsys.application.internaluser.dto.InternalUserResponse;
import bank_mega.corsys.domain.model.internaluser.InternalUser;

import java.util.Set;

public class InternalUserAssembler {

    public static InternalUserResponse toResponse(InternalUser saved) {
        if (saved == null) return null;
        return InternalUserResponse.builder()
                .userName(saved.getUserName().value())
                .nama(saved.getNama())
                .joinDate(saved.getJoinDate())
                .jabatan(saved.getJabatan() != null ? saved.getJabatan().value() : null)
                .approval1(saved.getApproval1())
                .approval2(saved.getApproval2())
                .lastLogin(saved.getLastLogin())
                .updatePass(saved.getUpdatePass())
                .status(saved.getStatus() != null ? saved.getStatus().value() : null)
                .count(saved.getCount() != null ? saved.getCount().value() : null)
                .email(saved.getEmail() != null ? saved.getEmail().value() : null)
                .area(saved.getArea() != null ? saved.getArea().value() : null)
                .jobTitle(saved.getJobTitle())
                .direktorat(saved.getDirektorat() != null ? saved.getDirektorat().value() : null)
                .sex(saved.getSex())
                .employee(saved.getEmployee() != null ? saved.getEmployee().value() : null)
                .mobile(saved.getMobile())
                .extOffice(saved.getExtOffice())
                .tglLahir(saved.getTglLahir())
                .pangkat(saved.getPangkat())
                .sessionId(saved.getSessionId())
                .usersCabang(saved.getUsersCabang() != null ? BranchAssembler.toResponse(saved.getUsersCabang()) : null)
                .usersBranch(saved.getUsersBranch() != null ? BranchAssembler.toResponse(saved.getUsersBranch()) : null)
                .build();
    }

    public static InternalUserResponse toResponse(InternalUser saved, Set<String> expands) {
        if (saved == null) return null;
        InternalUserResponse.InternalUserResponseBuilder builder = InternalUserResponse.builder()
                .userName(saved.getUserName().value())
                .nama(saved.getNama())
                .joinDate(saved.getJoinDate())
                .jabatan(saved.getJabatan() != null ? saved.getJabatan().value() : null)
                .approval1(saved.getApproval1())
                .approval2(saved.getApproval2())
                .lastLogin(saved.getLastLogin())
                .updatePass(saved.getUpdatePass())
                .status(saved.getStatus() != null ? saved.getStatus().value() : null)
                .count(saved.getCount() != null ? saved.getCount().value() : null)
                .email(saved.getEmail() != null ? saved.getEmail().value() : null)
                .area(saved.getArea() != null ? saved.getArea().value() : null)
                .jobTitle(saved.getJobTitle())
                .direktorat(saved.getDirektorat() != null ? saved.getDirektorat().value() : null)
                .sex(saved.getSex())
                .employee(saved.getEmployee() != null ? saved.getEmployee().value() : null)
                .mobile(saved.getMobile())
                .extOffice(saved.getExtOffice())
                .tglLahir(saved.getTglLahir())
                .pangkat(saved.getPangkat())
                .sessionId(saved.getSessionId())
;
        
        if (expands.contains("usersCabang") && saved.getUsersCabang() != null) {
            builder = builder.usersCabang(BranchAssembler.toResponse(saved.getUsersCabang()));
        }
        if (expands.contains("usersBranch") && saved.getUsersBranch() != null) {
            builder = builder.usersBranch(BranchAssembler.toResponse(saved.getUsersBranch()));
        }
        
        return builder.build();
    }

}
