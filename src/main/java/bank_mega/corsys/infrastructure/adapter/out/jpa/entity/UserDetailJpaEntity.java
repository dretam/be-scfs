package bank_mega.corsys.infrastructure.adapter.out.jpa.entity;

import bank_mega.corsys.infrastructure.adapter.out.jpa.entity.embeddable.AuditTrailEmbeddable;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "user_detail")
public class UserDetailJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private UserJpaEntity userId;

    @Column(name = "nama", length = 100)
    private String nama;

    @Column(name = "jabatan")
    private Integer jabatan;

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

    @Column(name = "mobile", length = 20)
    private String mobile;

    @Column(name = "tgl_lahir", length = 255)
    private String tglLahir;

    /**
     * Relations
     * - branch (cabang) M-1
     */
    @ManyToOne
    @JoinColumn(name = "cabang", referencedColumnName = "id_branch")
    private BranchJpaEntity usersCabang;

    /**
     * Relations
     * - branch (branch) M-1
     */
    @ManyToOne
    @JoinColumn(name = "branch", referencedColumnName = "id_branch")
    private BranchJpaEntity usersBranch;

    @Embedded
    private AuditTrailEmbeddable audit;

}
