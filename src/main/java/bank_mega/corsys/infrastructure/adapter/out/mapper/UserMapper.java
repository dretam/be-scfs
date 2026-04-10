package bank_mega.corsys.infrastructure.adapter.out.mapper;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;
import bank_mega.corsys.domain.model.user.*;
import bank_mega.corsys.infrastructure.adapter.out.jpa.entity.PermissionJpaEntity;
import bank_mega.corsys.infrastructure.adapter.out.jpa.entity.UserJpaEntity;
import bank_mega.corsys.infrastructure.adapter.out.jpa.entity.UserPermissionJpaEntity;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

public class UserMapper {

    public static User toDomain(@NotNull UserJpaEntity jpaEntity) {
        if (jpaEntity == null) throw new DomainRuleViolationException("JPA Entity is null (UserMapper)");
        return new User(
                new UserId(jpaEntity.getId()),
                new UserName(jpaEntity.getName()),
                new UserFullName(jpaEntity.getFullName()),
                new UserEmail(jpaEntity.getEmail()),
                new UserPassword(jpaEntity.getPassword()),
                new UserIsActive(jpaEntity.getIsActive()),
                new UserPhotoPath(jpaEntity.getPhotoPath()),
                CompanyMapper.toDomain(jpaEntity.getCompany()),
                RoleMapper.toDomain(jpaEntity.getRole()),
                RoleChildrenMapper.toDomain(jpaEntity.getRoleChildren()),
                AuditTrailEmbeddableMapper.toDomain(jpaEntity.getAudit())
        );
    }

    public static User toDomain(@NotNull UserJpaEntity jpaEntity, Set<String> expands) {
        if (jpaEntity == null) throw new DomainRuleViolationException("JPA Entity is null (UserMapper)");
        
        User user = new User(
                new UserId(jpaEntity.getId()),
                new UserName(jpaEntity.getName()),
                new UserFullName(jpaEntity.getFullName()),
                new UserEmail(jpaEntity.getEmail()),
                new UserPassword(jpaEntity.getPassword()),
                new UserIsActive(jpaEntity.getIsActive()),
                new UserPhotoPath(jpaEntity.getPhotoPath()),
                expands.contains("company") && jpaEntity.getCompany() != null
                        ? CompanyMapper.toDomain(jpaEntity.getCompany())
                        : null,
                expands.contains("role") && jpaEntity.getRole() != null
                        ? RoleMapper.toDomain(jpaEntity.getRole(), expands)
                        : null,
                expands.contains("roleChildren") && jpaEntity.getRoleChildren() != null
                        ? RoleChildrenMapper.toDomain(jpaEntity.getRoleChildren(), expands)
                        : null,
                AuditTrailEmbeddableMapper.toDomain(jpaEntity.getAudit())
        );

        if (expands.contains("userPermission") && jpaEntity.getPermissionsOverride() != null) {
            for (UserPermissionJpaEntity userPermissionJpaEntity : jpaEntity.getPermissionsOverride()) {
                user.addPermissionOverride(UserPermissionMapper.toDomain(userPermissionJpaEntity));
            }
        }

        return user;
    }

    public static UserJpaEntity toJpaEntity(User user) {
        UserJpaEntity jpaEntity = new UserJpaEntity();
        if (user.getId() != null) {
            jpaEntity.setId(user.getId().value());
        }
        jpaEntity.setName(user.getName().value());
        jpaEntity.setFullName(user.getFullName().value());
        jpaEntity.setEmail(user.getEmail().value());
        jpaEntity.setPassword(user.getPassword().value());
        jpaEntity.setIsActive(user.getIsActive().value());
        jpaEntity.setPhotoPath(user.getPhotoPath().value());
        jpaEntity.setCompany(CompanyMapper.toJpaEntity(user.getCompany()));
        jpaEntity.setRole(RoleMapper.toJpaEntity(user.getRole()));
        jpaEntity.setRoleChildren(RoleChildrenMapper.toJpaEntity(user.getRoleChildren()));
        jpaEntity.setAudit(AuditTrailEmbeddableMapper.toJpa(user.getAudit()));
        return jpaEntity;
    }

}
