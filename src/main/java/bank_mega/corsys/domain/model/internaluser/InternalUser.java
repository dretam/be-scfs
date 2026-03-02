package bank_mega.corsys.domain.model.internaluser;

import bank_mega.corsys.domain.model.common.AuditTrail;
import bank_mega.corsys.domain.model.branch.Branch;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class InternalUser {

    private final InternalUserName userName;
    private String nama;
    private LocalDate joinDate;
    private InternalUserJabatan jabatan;
    private String approval1;
    private String approval2;
    private LocalDateTime lastLogin;
    private LocalDateTime updatePass;
    private InternalUserStatus status;
    private InternalUserCount count;
    private InternalUserEmail email;
    private InternalUserArea area;
    private String jobTitle;
    private InternalUserDirektorat direktorat;
    private String sex;
    private InternalEmployeeType employee;
    private String mobile;
    private String password;
    private String extOffice;
    private String tglLahir;
    private String pangkat;
    private String sessionId;
    private Branch usersCabang;
    private Branch usersBranch;
    private AuditTrail audit;

    public InternalUser(
            InternalUserName userName,
            String nama,
            LocalDate joinDate,
            InternalUserJabatan jabatan,
            String approval1,
            String approval2,
            LocalDateTime lastLogin,
            LocalDateTime updatePass,
            InternalUserStatus status,
            InternalUserCount count,
            InternalUserEmail email,
            InternalUserArea area,
            String jobTitle,
            InternalUserDirektorat direktorat,
            String sex,
            InternalEmployeeType employee,
            String mobile,
            String password,
            String extOffice,
            String tglLahir,
            String pangkat,
            String sessionId,
            Branch usersCabang,
            Branch usersBranch,
            AuditTrail audit
    ) {
        this.userName = userName;
        this.nama = nama;
        this.joinDate = joinDate;
        this.jabatan = jabatan;
        this.approval1 = approval1;
        this.approval2 = approval2;
        this.lastLogin = lastLogin;
        this.updatePass = updatePass;
        this.status = status;
        this.count = count;
        this.email = email;
        this.area = area;
        this.jobTitle = jobTitle;
        this.direktorat = direktorat;
        this.sex = sex;
        this.employee = employee;
        this.mobile = mobile;
        this.password = password;
        this.extOffice = extOffice;
        this.tglLahir = tglLahir;
        this.pangkat = pangkat;
        this.sessionId = sessionId;
        this.usersCabang = usersCabang;
        this.usersBranch = usersBranch;
        this.audit = audit;
    }

    public void changeNama(String nama) {
        if (nama != null) {
            this.nama = nama;
        }
    }

    public void changeJoinDate(LocalDate joinDate) {
        if (joinDate != null) {
            this.joinDate = joinDate;
        }
    }

    public void changeJabatan(InternalUserJabatan jabatan) {
        if (jabatan != null) {
            this.jabatan = jabatan;
        }
    }

    public void changeApproval1(String approval1) {
        if (approval1 != null) {
            this.approval1 = approval1;
        }
    }

    public void changeApproval2(String approval2) {
        if (approval2 != null) {
            this.approval2 = approval2;
        }
    }

    public void changeLastLogin(LocalDateTime lastLogin) {
        if (lastLogin != null) {
            this.lastLogin = lastLogin;
        }
    }

    public void changeUpdatePass(LocalDateTime updatePass) {
        if (updatePass != null) {
            this.updatePass = updatePass;
        }
    }

    public void changeStatus(InternalUserStatus status) {
        if (status != null) {
            this.status = status;
        }
    }

    public void changeCount(InternalUserCount count) {
        if (count != null) {
            this.count = count;
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

    public void changeEmployee(InternalEmployeeType employee) {
        if (employee != null) {
            this.employee = employee;
        }
    }

    public void changeMobile(String mobile) {
        if (mobile != null) {
            this.mobile = mobile;
        }
    }

    public void changePassword(String password) {
        if (password != null) {
            this.password = password;
        }
    }

    public void changeExtOffice(String extOffice) {
        if (extOffice != null) {
            this.extOffice = extOffice;
        }
    }

    public void changeTglLahir(String tglLahir) {
        if (tglLahir != null) {
            this.tglLahir = tglLahir;
        }
    }

    public void changePangkat(String pangkat) {
        if (pangkat != null) {
            this.pangkat = pangkat;
        }
    }

    public void changeSessionId(String sessionId) {
        if (sessionId != null) {
            this.sessionId = sessionId;
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
