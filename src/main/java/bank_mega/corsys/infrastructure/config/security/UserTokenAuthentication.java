package bank_mega.corsys.infrastructure.config.security;

import bank_mega.corsys.domain.model.user.User;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.List;

public class UserTokenAuthentication extends AbstractAuthenticationToken {

    private final User user;
    private final Jwt jwt;

    public UserTokenAuthentication(User user, Jwt jwt) {
        super(List.of(RoleGrantedAuthorityAdapter.from(user.getRole()))); // role dari domain user
        this.user = user;
        this.jwt = jwt;
        setAuthenticated(true);
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

}
