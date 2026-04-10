package bank_mega.corsys.infrastructure.adapter.out.jpa.entity;

import bank_mega.corsys.domain.model.user.UserType;
import bank_mega.corsys.infrastructure.adapter.out.jpa.entity.embeddable.AuditTrailEmbeddable;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.PostgreSQLEnumJdbcType;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class UserJpaEntity {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false, unique = true, name = "username")
    private String name;

    @Column(nullable = false, name = "full_name")
    private String fullName;

    @Column(nullable = false, name = "email")
    private String email;

    @Column(nullable = false, name = "is_active")
    private Boolean isActive;

    @Column(nullable = false, name = "photo_path")
    private String photoPath;

    @Column(nullable = false, name = "password")
    private String password;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_code")
    private RoleJpaEntity role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "role_children_code",
        referencedColumnName = "code"
    )
    private RoleChildrenJpaEntity roleChildren;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private CompanyJpaEntity company;

    @Embedded
    private AuditTrailEmbeddable audit;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<UserPermissionJpaEntity> permissionsOverride = new HashSet<>();
}
