package bank_mega.corsys.infrastructure.adapter.out.jpa.entity.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Embeddable class for UserPermission composite primary key.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class UserPermissionIdEmbeddable {

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(name = "permission_id", nullable = false)
    private UUID permissionId;

}
