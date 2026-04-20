package bank_mega.corsys.infrastructure.adapter.in.api.v1;

import bank_mega.corsys.application.common.dto.ReadListResponse;
import bank_mega.corsys.application.common.dto.ReadRetrieveResponse;
import bank_mega.corsys.application.emailtemplate.command.UpdateEmailTemplateCommand;
import bank_mega.corsys.application.emailtemplate.dto.EmailTemplateResponse;
import bank_mega.corsys.application.emailtemplate.usecase.ListEmailTemplateUseCase;
import bank_mega.corsys.application.emailtemplate.usecase.RetrieveEmailTemplateUseCase;
import bank_mega.corsys.application.emailtemplate.usecase.UpdateEmailTemplateUseCase;
import bank_mega.corsys.domain.model.emailtemplate.EmailTemplateVariant;
import bank_mega.corsys.domain.model.user.User;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Email Templates")
@RestController
@RequestMapping("/api/v1/email-templates")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authorization")
@RateLimiter(name = "global")
public class EmailTemplateApi {

    private final ListEmailTemplateUseCase listEmailTemplateUseCase;
    private final RetrieveEmailTemplateUseCase retrieveEmailTemplateUseCase;
    private final UpdateEmailTemplateUseCase updateEmailTemplateUseCase;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ReadListResponse<List<EmailTemplateResponse>> list() {
        List<EmailTemplateResponse> data = listEmailTemplateUseCase.execute();
        return ReadListResponse.<List<EmailTemplateResponse>>builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(data)
                .build();
    }

    @GetMapping(path = "/{variant}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ReadRetrieveResponse<EmailTemplateResponse> retrieve(@PathVariable EmailTemplateVariant variant) {
        EmailTemplateResponse data = retrieveEmailTemplateUseCase.execute(variant);
        return ReadRetrieveResponse.<EmailTemplateResponse>builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(data)
                .build();
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ReadRetrieveResponse<EmailTemplateResponse> update(
            @AuthenticationPrincipal User authPrincipal,
            @RequestBody UpdateEmailTemplateCommand command
    ) {
        EmailTemplateResponse data = updateEmailTemplateUseCase.execute(command, authPrincipal);
        return ReadRetrieveResponse.<EmailTemplateResponse>builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(data)
                .build();
    }
}
