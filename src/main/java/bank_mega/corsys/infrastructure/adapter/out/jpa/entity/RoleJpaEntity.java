package bank_mega.corsys.infrastructure.adapter.out.jpa.entity;

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
@Table(name = "roles")
public class RoleJpaEntity {

    @Id
    @Column(unique = true)
    private String code;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String icon;

    private String description;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "role_permissions",
            joinColumns = @JoinColumn(name = "role_code"),
            inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    @Builder.Default
    private Set<PermissionJpaEntity> permissions = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "role_menus",
            joinColumns = @JoinColumn(name = "role_code"),
            inverseJoinColumns = @JoinColumn(name = "menu_id")
    )
    @Builder.Default
    private Set<MenuJpaEntity> menus = new HashSet<>();

    @Embedded
    private AuditTrailEmbeddable audit;

}
