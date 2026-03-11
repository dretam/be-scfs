package bank_mega.corsys.infrastructure.adapter.out.jpa.entity;

import bank_mega.corsys.domain.model.user.UserType;
import bank_mega.corsys.infrastructure.adapter.out.jpa.entity.embeddable.AuditTrailEmbeddable;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.PostgreSQLEnumJdbcType;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class UserJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, name = "username")
    private String name;

    private String email;

    private String password;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private RoleJpaEntity role;

    @Enumerated(EnumType.STRING)
    @JdbcType(PostgreSQLEnumJdbcType.class)
    @Column(name = "type", nullable = false, columnDefinition = "user_type")
    private UserType type = UserType.INTERNAL;

    @Embedded
    private AuditTrailEmbeddable audit;

    @OneToOne(mappedBy = "userId", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private UserDetailJpaEntity userDetail;

}
