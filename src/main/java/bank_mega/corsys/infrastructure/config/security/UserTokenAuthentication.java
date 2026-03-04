package bank_mega.corsys.infrastructure.config.security;

import bank_mega.corsys.domain.model.user.User;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.ArrayList;
import java.util.List;

public class UserTokenAuthentication extends AbstractAuthenticationToken {

    private final User user;
    private final Jwt jwt;

    public UserTokenAuthentication(User user, Jwt jwt) {
        super(buildAuthorities(user));
        this.user = user;
        this.jwt = jwt;
        setAuthenticated(true);
    }

    private static List<GrantedAuthority> buildAuthorities(User user) {
        List<GrantedAuthority> authorities = new ArrayList<>();

        // Add role authority
        if (user.getRole() != null) {
            authorities.add(RoleGrantedAuthorityAdapter.from(user.getRole()));
        }

        // Add permission authorities
        if (user.getRole() != null && user.getRole().getPermissions() != null) {
            user.getRole().getPermissions().stream()
                    .map(permission -> new PermissionGrantedAuthority(permission.getCode().value()))
                    .forEach(authorities::add);
        }

        return authorities;
    }

    @Override
    public @NotNull Jwt getCredentials() {
        return this.jwt;
    }

    @Override
    public @NotNull User getPrincipal() {
        return this.user;
    }

    @Override
    public @NotNull String getName() {
        return user.getName().value();
    }

    /**
     * Inner class for permission-based authorities.
     */
    public static class PermissionGrantedAuthority implements GrantedAuthority {
        private final String permissionCode;

        public PermissionGrantedAuthority(String permissionCode) {
            this.permissionCode = permissionCode;
        }

        @Override
        public String getAuthority() {
            return "PERMISSION:" + permissionCode;
        }
    }

}
