package bank_mega.corsys.infrastructure.config.security;

import bank_mega.corsys.domain.model.role.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@RequiredArgsConstructor
public class RoleGrantedAuthorityAdapter {

    public static GrantedAuthority from(Role role) {
        return new SimpleGrantedAuthority(role.getName().toString());
    }

}
