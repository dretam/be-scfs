package bank_mega.corsys.domain.model.branch;

import bank_mega.corsys.domain.model.common.AuditTrail;
import lombok.Getter;

@Getter
public class Branch {

    private final BranchId id;
    private BranchCode idBranch;
    private BranchName branchName;
    private Boolean flagDel;
    private BranchCategory category;
    private BranchRegional regional;
    private String address;
    private BranchArea area;
    private BranchDirektorat direktorat;
    private Long modId;
    private Long telepon;
    private Long faximile;
    private BranchAbbreviation singkatan;
    private AuditTrail audit;

    public Branch(
            BranchId id,
            BranchCode idBranch,
            BranchName branchName,
            Boolean flagDel,
            BranchCategory category,
            BranchRegional regional,
            String address,
            BranchArea area,
            BranchDirektorat direktorat,
            Long modId,
            Long telepon,
            Long faximile,
            BranchAbbreviation singkatan,
            AuditTrail audit
    ) {
        this.id = id;
        this.idBranch = idBranch;
        this.branchName = branchName;
        this.flagDel = flagDel;
        this.category = category;
        this.regional = regional;
        this.address = address;
        this.area = area;
        this.direktorat = direktorat;
        this.modId = modId;
        this.telepon = telepon;
        this.faximile = faximile;
        this.singkatan = singkatan;
        this.audit = audit;
    }

    public void changeIdBranch(BranchCode idBranch) {
        if (idBranch != null) {
            this.idBranch = idBranch;
        }
    }

    public void changeBranchName(BranchName branchName) {
        if (branchName != null) {
            this.branchName = branchName;
        }
    }

    public void changeFlagDel(Boolean flagDel) {
        if (flagDel != null) {
            this.flagDel = flagDel;
        }
    }

    public void changeCategory(BranchCategory category) {
        if (category != null) {
            this.category = category;
        }
    }

    public void changeRegional(BranchRegional regional) {
        if (regional != null) {
            this.regional = regional;
        }
    }

    public void changeAddress(String address) {
        if (address != null) {
            this.address = address;
        }
    }

    public void changeArea(BranchArea area) {
        if (area != null) {
            this.area = area;
        }
    }

    public void changeDirektorat(BranchDirektorat direktorat) {
        if (direktorat != null) {
            this.direktorat = direktorat;
        }
    }

    public void changeModId(Long modId) {
        if (modId != null) {
            this.modId = modId;
        }
    }

    public void changeTelepon(Long telepon) {
        if (telepon != null) {
            this.telepon = telepon;
        }
    }

    public void changeFaximile(Long faximile) {
        if (faximile != null) {
            this.faximile = faximile;
        }
    }

    public void changeSingkatan(BranchAbbreviation singkatan) {
        if (singkatan != null) {
            this.singkatan = singkatan;
        }
    }

    public void updateAudit(Long updatedBy) {
        this.audit = this.audit.update(updatedBy);
    }

    public void deleteAudit(Long deletedBy) {
        this.audit = this.audit.delete(deletedBy);
    }

}
