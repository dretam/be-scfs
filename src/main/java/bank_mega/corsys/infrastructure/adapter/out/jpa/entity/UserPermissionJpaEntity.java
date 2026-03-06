package bank_mega.corsys.infrastructure.adapter.out.jpa.entity;

import bank_mega.corsys.domain.model.userpermission.Effect;
import bank_mega.corsys.infrastructure.adapter.out.jpa.entity.embeddable.AuditTrailEmbeddable;
import bank_mega.corsys.infrastructure.adapter.out.jpa.entity.embeddable.UserPermissionIdEmbeddable;
import jakarta.persistence.*;
import lombok.*;

/**
 * JPA Entity for User Permission Override.
 * Allows overriding role-based permissions on a per-user basis.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "user_permissions")
public class UserPermissionJpaEntity {

    @EmbeddedId
    private UserPermissionIdEmbeddable id;

    @MapsId("userId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserJpaEntity user;

    @MapsId("permissionId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "permission_id", nullable = false)
    private PermissionJpaEntity permission;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Effect effect;
}
