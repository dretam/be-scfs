package bank_mega.corsys.infrastructure.adapter.in.api.v1;

import bank_mega.corsys.application.assembler.UserAssembler;
import bank_mega.corsys.application.auth.command.LoginJWTCommand;
import bank_mega.corsys.application.auth.command.RefreshJWTCommand;
import bank_mega.corsys.application.auth.dto.LoginJWTResponse;
import bank_mega.corsys.application.auth.dto.RefreshJWTResponse;
import bank_mega.corsys.application.auth.usecase.LoginJWTUseCase;
import bank_mega.corsys.application.auth.usecase.RefreshJWTUseCase;
import bank_mega.corsys.application.common.dto.ReadRetrieveResponse;
import bank_mega.corsys.application.user.dto.UserResponse;
import bank_mega.corsys.application.user.usecase.*;
import bank_mega.corsys.domain.model.user.User;
import bank_mega.corsys.infrastructure.util.ParserUtil;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@Tag(name = "Auth")
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@RateLimiter(name = "global")
public class AuthApi {

    private final RetrieveUserUseCase retrieveUserUseCase;
    private final LoginJWTUseCase loginJWTUseCase;
    private final RefreshJWTUseCase refreshJWTUseCase;

    @SecurityRequirement(name = "Bearer Authorization")
    @GetMapping(
            path = "/session",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ReadRetrieveResponse<UserResponse> session(
            @AuthenticationPrincipal User authPrincipal,
            @RequestParam(value = "expands", required = false) String expand
    ) {
        User data = this.retrieveUserUseCase.execute(authPrincipal.getId().value(), ParserUtil.expandParse(expand));
        return ReadRetrieveResponse.<UserResponse>builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(UserAssembler.toResponse(data))
                .build();
    }

    @Operation(summary = "Login")
    @SecurityRequirements
    @PostMapping(
            path = "/login",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public LoginJWTResponse login(@RequestBody LoginJWTCommand command) {
        return loginJWTUseCase.execute(command);
    }

    @Operation(summary = "Refresh Access JWT Token")
    @SecurityRequirements
    @PostMapping(
            path = "/refresh",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public RefreshJWTResponse refresh(@RequestBody RefreshJWTCommand command) {
        return refreshJWTUseCase.execute(command);
    }

}
