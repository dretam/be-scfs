package bank_mega.corsys.infrastructure.config.seeder;

import bank_mega.corsys.domain.exception.RoleNotFoundException;
import bank_mega.corsys.domain.model.common.AuditTrail;
import bank_mega.corsys.domain.model.role.Role;
import bank_mega.corsys.domain.model.role.RoleIcon;
import bank_mega.corsys.domain.model.role.RoleName;
import bank_mega.corsys.domain.model.user.User;
import bank_mega.corsys.domain.model.user.UserEmail;
import bank_mega.corsys.domain.model.user.UserName;
import bank_mega.corsys.domain.model.user.UserPassword;
import bank_mega.corsys.domain.repository.RoleRepository;
import bank_mega.corsys.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import jakarta.validation.constraints.NotNull;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class UserSeeder implements ApplicationRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    @Override
    public void run(@NotNull ApplicationArguments args) {
        if (roleRepository.count() == 0) {
            this.setRole("ROLE_APP", "lucide:cog", "For app only, used for scheduler");
            this.setRole("ROLE_SU", "lucide:crown", "Super user that have all privileges, used by developer");
            this.setRole("ROLE_ADMIN", "lucide:clipboard-pen-line", "Administration privileges");
            this.setRole("ROLE_VIEW", "lucide:eye", "View only privileges");
        }

        if (userRepository.count() == 0) {
            this.setUser(
                    new UserName("system"),
                    new UserEmail("hexagonal.apps@yopmail.com"),
                    new RoleName("ROLE_APP")
            );
        }
    }


    private void setUser(UserName name, UserEmail email, RoleName roleName) {
        Role role = this.roleRepository.findFirstByName(roleName).orElseThrow(
                () -> new RoleNotFoundException(roleName)
        );
        User user = new User(
                null,
                name,
                email,
                new UserPassword(userRepository.hashPassword("Qwerty123456")),
                role,
                AuditTrail.create(0L)
        );
        this.userRepository.save(user);
    }

    private void setRole(String name, String icon, String description) {
        Role existingRole = roleRepository.findFirstByName(new RoleName(name)).orElse(null);
        if (existingRole == null) {
            Role newRole = new Role(
                    null,
                    new RoleName(name),
                    new RoleIcon(icon),
                    description,
                    AuditTrail.create(0L)
            );
            roleRepository.save(newRole);
        }
    }

}
