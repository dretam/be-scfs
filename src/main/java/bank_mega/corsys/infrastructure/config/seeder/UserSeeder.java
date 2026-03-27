//package bank_mega.corsys.infrastructure.config.seeder;
//
//import bank_mega.corsys.domain.exception.RoleNotFoundException;
//import bank_mega.corsys.domain.model.common.AuditTrail;
//import bank_mega.corsys.domain.model.permission.Permission;
//import bank_mega.corsys.domain.model.permission.PermissionCode;
//import bank_mega.corsys.domain.model.role.Role;
//import bank_mega.corsys.domain.model.role.RoleName;
//import bank_mega.corsys.domain.model.user.*;
//import bank_mega.corsys.domain.model.userpermission.Effect;
//import bank_mega.corsys.domain.model.userpermission.UserPermission;
//import bank_mega.corsys.domain.model.userpermission.UserPermissionId;
//import bank_mega.corsys.domain.repository.PermissionRepository;
//import bank_mega.corsys.domain.repository.RoleRepository;
//import bank_mega.corsys.domain.repository.UserPermissionRepository;
//import bank_mega.corsys.domain.repository.UserRepository;
//import lombok.RequiredArgsConstructor;
//import jakarta.validation.constraints.NotNull;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.boot.ApplicationArguments;
//import org.springframework.boot.ApplicationRunner;
//import org.springframework.core.annotation.Order;
//import org.springframework.stereotype.Component;
//
//
//@Component
//@RequiredArgsConstructor
//@Order(2)
//@Slf4j
//public class UserSeeder implements ApplicationRunner {
//
//    private final RoleRepository roleRepository;
//    private final UserRepository userRepository;
//    private final PermissionRepository permissionRepository;
//    private final UserPermissionRepository userPermissionRepository;
//
//    @Override
//    public void run(@NotNull ApplicationArguments args) {
//        if (userRepository.count() == 0) {
//            this.setUser(
//                    new UserName("system"),
//                    new UserEmail("hexagonal.apps@yopmail.com"),
//                    new RoleName("ROLE_SU")
//            );
//
//            User johnDoe = this.setUser(
//                    new UserName("john.doe"),
//                    new UserEmail("john.doe@yopmail.com"),
//                    new RoleName("ROLE_VIEW")
//            );
//
//            this.setUserPermissionOverride(johnDoe, "USER_CREATE", Effect.ALLOW);
//            this.setUserPermissionOverride(johnDoe, "USER_READ", Effect.DENY);
//        }
//    }
//
//
//    private User setUser(UserName name, UserEmail email, RoleName roleName) {
//        Role role = this.roleRepository.findFirstByName(roleName).orElseThrow(
//                () -> new RoleNotFoundException(roleName)
//        );
//        User user = new User(
//                null,
//                name,
//                email,
//                new UserPassword(userRepository.hashPassword("Qwerty123456")),
//                role,
//                UserType.EXTERNAL,
//                AuditTrail.create(0L)
//        );
//        return this.userRepository.save(user);
//    }
//
//    private void setUserPermissionOverride(User user, String permissionCode, Effect effect) {
//        Permission permission = this.permissionRepository
//                .findFirstByCode(new PermissionCode(permissionCode))
//                .orElseThrow(() -> new RuntimeException("Permission not found: " + permissionCode));
//
//        UserPermission userPermission = new UserPermission(
//                new UserPermissionId(user.getId().value(), permission.getId().value()),
//                user,
//                permission,
//                effect
//        );
//
//        log.info("User " + user.getId().value());
//        log.info("Permission " + permission.getId().value());
//        log.info("Effect " + effect.name());
//
//        this.userPermissionRepository.save(userPermission);
//    }
//
//}
