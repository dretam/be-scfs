package bank_mega.corsys.infrastructure.adapter.out.mapper;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;
import bank_mega.corsys.domain.model.permission.Permission;
import bank_mega.corsys.domain.model.permission.PermissionId;
import bank_mega.corsys.domain.model.user.User;
import bank_mega.corsys.domain.model.user.UserId;
import bank_mega.corsys.domain.model.userpermission.UserPermission;
import bank_mega.corsys.domain.model.userpermission.UserPermissionId;
import bank_mega.corsys.infrastructure.adapter.out.jpa.entity.PermissionJpaEntity;
import bank_mega.corsys.infrastructure.adapter.out.jpa.entity.UserJpaEntity;
import bank_mega.corsys.infrastructure.adapter.out.jpa.entity.UserPermissionJpaEntity;
import bank_mega.corsys.infrastructure.adapter.out.jpa.entity.embeddable.UserPermissionIdEmbeddable;
import jakarta.validation.constraints.NotNull;

public class UserPermissionMapper {

    public static UserPermission toDomain(@NotNull UserPermissionJpaEntity jpaEntity) {
        if (jpaEntity == null) throw new DomainRuleViolationException("JPA Entity is null");

        UserPermissionId id = new UserPermissionId(
                jpaEntity.getId().getUserId(),
                jpaEntity.getId().getPermissionId()
        );

        User user = null;
        if (jpaEntity.getUser() != null) {
            user = UserMapper.toDomain(jpaEntity.getUser());
        }

        Permission permission = null;
        if (jpaEntity.getPermission() != null) {
            permission = PermissionMapper.toDomain(jpaEntity.getPermission());
        }

        UserPermission userPermission = new UserPermission(
                id,
                user,
                permission,
                jpaEntity.getEffect()
        );

        return userPermission;
    }

    public static UserPermissionJpaEntity toJpaEntity(UserPermission domainEntity) {
        UserPermissionJpaEntity jpaEntity = new UserPermissionJpaEntity();

        UserPermissionIdEmbeddable idEmbeddable = new UserPermissionIdEmbeddable();
        idEmbeddable.setUserId(domainEntity.getId().userId());
        idEmbeddable.setPermissionId(domainEntity.getId().permissionId());
        jpaEntity.setId(idEmbeddable);

        // Set user reference (lazy loaded)
        if (domainEntity.getUser() != null && domainEntity.getUser().getId() != null) {
            UserJpaEntity userJpa = new UserJpaEntity();
            userJpa.setId(domainEntity.getUser().getId().value());
            jpaEntity.setUser(userJpa);
        }

        if (domainEntity.getPermission() != null && domainEntity.getPermission().getId() != null) {
            PermissionJpaEntity permissionJpa = new PermissionJpaEntity();
            permissionJpa.setId(domainEntity.getPermission().getId().value());
            jpaEntity.setPermission(permissionJpa);
        }

        jpaEntity.setEffect(domainEntity.getEffect());

        return jpaEntity;
    }

}
