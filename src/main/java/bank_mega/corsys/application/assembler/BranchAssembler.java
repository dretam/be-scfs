package bank_mega.corsys.application.assembler;

import bank_mega.corsys.application.branch.dto.BranchResponse;
import bank_mega.corsys.domain.model.branch.Branch;

public class BranchAssembler {

    public static BranchResponse toResponse(Branch saved) {
        if (saved == null) return null;
        return BranchResponse.builder()
                .id(saved.getId().value())
                .idBranch(saved.getIdBranch() != null ? saved.getIdBranch().value() : null)
                .branchName(saved.getBranchName() != null ? saved.getBranchName().value() : null)
                .flagDel(saved.getFlagDel())
                .category(saved.getCategory() != null ? saved.getCategory().value() : null)
                .regional(saved.getRegional() != null ? saved.getRegional().value() : null)
                .address(saved.getAddress())
                .area(saved.getArea() != null ? saved.getArea().value() : null)
                .direktorat(saved.getDirektorat() != null ? saved.getDirektorat().value() : null)
                .modId(saved.getModId())
                .telepon(saved.getTelepon())
                .faximile(saved.getFaximile())
                .singkatan(saved.getSingkatan() != null ? saved.getSingkatan().value() : null)
                .build();
    }

}
