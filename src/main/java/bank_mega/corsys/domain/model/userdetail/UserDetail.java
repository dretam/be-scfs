package bank_mega.corsys.domain.model.userdetail;

import bank_mega.corsys.domain.model.branch.Branch;
import bank_mega.corsys.domain.model.common.AuditTrail;
import bank_mega.corsys.domain.model.internaluser.InternalUserArea;
import bank_mega.corsys.domain.model.internaluser.InternalUserDirektorat;
import bank_mega.corsys.domain.model.internaluser.InternalUserEmail;
import bank_mega.corsys.domain.model.internaluser.InternalUserJabatan;
import bank_mega.corsys.domain.model.user.UserId;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDetail {

    private final UserDetailId id;
    private final UserId userId;
    private String nama;
    private InternalUserJabatan jabatan;
    private InternalUserEmail email;
    private InternalUserArea area;
    private String jobTitle;
    private InternalUserDirektorat direktorat;
    private String sex;
    private String mobile;
    private String tglLahir;
    private Branch usersCabang;
    private Branch usersBranch;
    private AuditTrail audit;

    public UserDetail(
            UserDetailId id,
            UserId userId,
            String nama,
            InternalUserJabatan jabatan,
            InternalUserEmail email,
            InternalUserArea area,
            String jobTitle,
            InternalUserDirektorat direktorat,
            String sex,
            String mobile,
            String tglLahir,
            Branch usersCabang,
            Branch usersBranch,
            AuditTrail audit
    ) {
        this.id = id;
        this.userId = userId;
        this.nama = nama;
        this.jabatan = jabatan;
        this.email = email;
        this.area = area;
        this.jobTitle = jobTitle;
        this.direktorat = direktorat;
        this.sex = sex;
        this.mobile = mobile;
        this.tglLahir = tglLahir;
        this.usersCabang = usersCabang;
        this.usersBranch = usersBranch;
        this.audit = audit;
    }

    public void changeNama(String nama) {
        if (nama != null) {
            this.nama = nama;
        }
    }

    public void changeJabatan(InternalUserJabatan jabatan) {
        if (jabatan != null) {
            this.jabatan = jabatan;
        }
    }

    public void changeEmail(InternalUserEmail email) {
        if (email != null) {
            this.email = email;
        }
    }

    public void changeArea(InternalUserArea area) {
        if (area != null) {
            this.area = area;
        }
    }

    public void changeJobTitle(String jobTitle) {
        if (jobTitle != null) {
            this.jobTitle = jobTitle;
        }
    }

    public void changeDirektorat(InternalUserDirektorat direktorat) {
        if (direktorat != null) {
            this.direktorat = direktorat;
        }
    }

    public void changeSex(String sex) {
        if (sex != null) {
            this.sex = sex;
        }
    }

    public void changeMobile(String mobile) {
        if (mobile != null) {
            this.mobile = mobile;
        }
    }

    public void changeTglLahir(String tglLahir) {
        if (tglLahir != null) {
            this.tglLahir = tglLahir;
        }
    }

    public void changeUsersCabang(Branch usersCabang) {
        if (usersCabang != null) {
            this.usersCabang = usersCabang;
        }
    }

    public void changeUsersBranch(Branch usersBranch) {
        if (usersBranch != null) {
            this.usersBranch = usersBranch;
        }
    }

    public void updateAudit(Long updatedBy) {
        this.audit = this.audit.update(updatedBy);
    }

    public void deleteAudit(Long deletedBy) {
        this.audit = this.audit.delete(deletedBy);
    }

}
