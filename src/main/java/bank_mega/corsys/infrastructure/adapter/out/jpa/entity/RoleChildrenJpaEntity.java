package bank_mega.corsys.infrastructure.adapter.out.jpa.entity;

import bank_mega.corsys.domain.model.role.Role;
import bank_mega.corsys.infrastructure.adapter.out.jpa.entity.embeddable.AuditTrailEmbeddable;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "role_children")
public class RoleChildrenJpaEntity {

    @Id
    @Column(unique = true)
    private String code;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String icon;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "parent_code",
        referencedColumnName = "code"
    )
    private RoleJpaEntity role;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "role_children_permissions",
            joinColumns = @JoinColumn(name = "role_code"),
            inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    @Builder.Default
    private Set<PermissionJpaEntity> permissions = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "role_children_menus",
            joinColumns = @JoinColumn(name = "role_code"),
            inverseJoinColumns = @JoinColumn(name = "menu_id")
    )
    @Builder.Default
    private Set<MenuJpaEntity> menus = new HashSet<>();

    @Embedded
    private AuditTrailEmbeddable audit;

}
