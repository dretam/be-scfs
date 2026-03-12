package bank_mega.corsys.infrastructure.adapter.in.api.v1;

import bank_mega.corsys.application.common.dto.ReadRetrieveResponse;
import bank_mega.corsys.application.document.command.CreateDocumentMultipartCommand;
import bank_mega.corsys.application.document.dto.OCRResponse;
import bank_mega.corsys.application.document.usecase.CreateDocumentUseCase;
import bank_mega.corsys.domain.model.user.User;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "Documents")
@Validated
@RestController
@RequestMapping("/api/v1/documents")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authorization")
@RateLimiter(name = "global")
public class DocumentApi {

    private final CreateDocumentUseCase createDocumentUseCase;

    @PostMapping(
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ReadRetrieveResponse<List<OCRResponse>> create(
            @AuthenticationPrincipal User authPrincipal,
            @RequestParam("file") MultipartFile file
    ) throws Exception {
        var command = CreateDocumentMultipartCommand.builder()
                .file(file)
                .build();

        List<OCRResponse> data = this.createDocumentUseCase.execute(command, authPrincipal);

        return ReadRetrieveResponse.<List<OCRResponse>>builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(data)
                .build();
    }
}