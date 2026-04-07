package bank_mega.corsys.infrastructure.config;

import bank_mega.corsys.infrastructure.config.security.AccessLogFilter;
import bank_mega.corsys.infrastructure.config.security.UserContextFilter;
import com.nimbusds.jose.jwk.source.ImmutableSecret;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.oauth2.server.resource.web.authentication.BearerTokenAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    @Value("${jwt.algorithm}")
    private String jwtAlgorithm;

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.issuer}")
    private String jwtIssuer;

    @Value("${jwt.audience}")
    private String jwtAudience;

    private final UserContextFilter userContextFilter;
    private final AccessLogFilter accessLogFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // TODO : /db-scheduler-api/** pada permitAll hanya untuk kebutuhan Development, silahkan untuk dihapus apabila production phase
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(authorize ->
                        authorize
                                .requestMatchers(
                                        "/health",
                                        "/actuator/**",
                                        "/swagger-ui.html",
                                        "/swagger-ui/**",
                                        "/v3/api-docs/**",
                                        "/api/*/auth/login",
                                        "/api/*/auth/refresh",
                                        "/api/*/users/changePass",
                                        "/api/*/users/sendTokenChangePass",
                                        "/db-scheduler-api/**"
                                )
                                .permitAll()
                                .anyRequest()
                                .authenticated()
                ).oauth2ResourceServer(oauth2 ->
                        oauth2.jwt(Customizer.withDefaults())
                )
                .addFilterAfter(
                        this.userContextFilter,
                        BearerTokenAuthenticationFilter.class
                )
                .addFilterAfter(
                        this.accessLogFilter,
                        UserContextFilter.class
                );
        return http.build();
    }

    @Bean
    public SecretKey jwtSecretKey() {
        return new SecretKeySpec(
                jwtSecret.getBytes(),
                jwtAlgorithm
        );
    }

    @Bean
    public JwtEncoder jwtEncoder(SecretKey jwtSecretKey) {
        return new NimbusJwtEncoder(
                new ImmutableSecret<>(jwtSecretKey)
        );
    }

    @Bean
    public JwtDecoder jwtDecoder(SecretKey jwtSecretKey) {
        NimbusJwtDecoder jwtDecoder = NimbusJwtDecoder.withSecretKey(jwtSecretKey).build();

        // Issuer validator
        OAuth2TokenValidator<Jwt> issuerValidator =
                JwtValidators.createDefaultWithIssuer(this.jwtIssuer);

        // Audience validator
        OAuth2TokenValidator<Jwt> audienceValidator =
                new JwtAudienceValidator(this.jwtAudience);

        OAuth2TokenValidator<Jwt> validator =
                new DelegatingOAuth2TokenValidator<>(issuerValidator, audienceValidator);

        jwtDecoder.setJwtValidator(validator);
        return jwtDecoder;
    }

}
