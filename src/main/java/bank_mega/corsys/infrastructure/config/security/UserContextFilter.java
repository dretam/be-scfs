package bank_mega.corsys.infrastructure.config.security;

import bank_mega.corsys.application.user.usecase.RetrieveUserUseCase;
import bank_mega.corsys.infrastructure.util.ParserUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Component
@RequiredArgsConstructor
public class UserContextFilter extends OncePerRequestFilter {

    private final RetrieveUserUseCase retrieveUserUseCase;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 1. Bukan JWT → Biarkan Lewat
        if (!(authentication instanceof JwtAuthenticationToken jwtAuth)) {
            filterChain.doFilter(request, response);
            return;
        }

        Jwt jwt = jwtAuth.getToken();
        String userId = jwt.getClaim("uid");
        if (userId == null) {
            filterChain.doFilter(request, response);
            return;
        }

        // 2. Detail session authentication
        UserTokenAuthentication detailAuth = new UserTokenAuthentication(
                this.retrieveUserUseCase.execute(userId, ParserUtil.expandParse("role,permissions")),
                jwt
        );

        SecurityContextHolder.getContext().setAuthentication(detailAuth);

        // 3. Lanjutkan
        filterChain.doFilter(request, response);
    }
}