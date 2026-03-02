package bank_mega.corsys.infrastructure.adapter.out.jpa.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "internal_user")
public class InternalUserJpaEntity {

    @Id
    @Column(name = "user_name", length = 15, nullable = false)
    private String userName;

    @Column(name = "nama", length = 100)
    private String nama;

    @Column(name = "join_date")
    private LocalDate joinDate;

    @Column(name = "jabatan")
    private Integer jabatan;

    @Column(name = "approval1", length = 10)
    private String approval1;

    @Column(name = "approval2", length = 10)
    private String approval2;

    @Column(name = "last_login")
    private LocalDateTime lastLogin;

    @Column(name = "update_pass")
    private LocalDateTime updatePass;

    @Column(name = "status")
    private Integer status;

    @Column(name = "count")
    private Integer count;

    @Column(name = "email", length = 200)
    private String email;


    @Column(name = "area", length = 10)
    private String area;

    @Column(name = "job_title", length = 100)
    private String jobTitle;

    @Column(name = "direktorat", length = 10)
    private String direktorat;

    @Column(name = "sex", length = 10)
    private String sex;

    @Column(name = "employee", length = 2)
    private String employee;

    @Column(name = "mobile", length = 20)
    private String mobile;

    @Column(name = "password", length = 50)
    private String password;

    @Column(name = "ext_office", length = 50)
    private String extOffice;

    @Column(name = "tgl_lahir", length = 255)
    private String tglLahir;

    @Column(name = "pangkat", length = 255)
    private String pangkat;

    @Column(name = "session_id", length = 255)
    private String sessionId;

    /**
     * Relations
     * - branch (cabang) M-1
     */
    @ManyToOne
    @JoinColumn(name = "cabang", referencedColumnName = "id_branch")
    @JsonIgnore
    private BranchJpaEntity usersCabang;

    /**
     * Relations
     * - branch (branch) M-1
     */
    @ManyToOne
    @JoinColumn(name = "branch", referencedColumnName = "id_branch")
    @JsonIgnore
    private BranchJpaEntity usersBranch;
}
