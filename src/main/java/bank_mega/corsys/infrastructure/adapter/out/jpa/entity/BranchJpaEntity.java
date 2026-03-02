package bank_mega.corsys.infrastructure.adapter.out.jpa.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "branch")
public class BranchJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "branch_generator")
    @SequenceGenerator(name = "branch_generator", sequenceName = "branch_id_seq", allocationSize = 1)
    private Long id;

    @Column(name = "id_branch")
    private String idBranch;

    @Column(name = "branch_name")
    private String branchName;

    @Column(name = "flag_del")
    private Boolean flagDel;

    @Column(name = "category")
    private String category;

    @Column(name = "regional")
    private String regional;

    @Column(name = "address")
    private String address;

    @Column(name = "area")
    private String area;

    @Column(name = "direktorat")
    private String direktorat;

    @Column(name = "mod_id")
    private Long modId;

    @Column(name = "telepon")
    private Long telepon;

    @Column(name = "faximile")
    private Long faximile;

    @Column(name = "singkatan")
    private String singkatan;


    /**
     * Relations
     * - users (cabang) 1-M
     */
    @OneToMany(mappedBy = "usersCabang", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<InternalUserJpaEntity> usersCabang;

    /**
     * Relations
     * - users (branch) 1-M
     */
    @OneToMany(mappedBy = "usersBranch", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<InternalUserJpaEntity> usersBranch;
}
