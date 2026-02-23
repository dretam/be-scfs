package bank_mega.corsys.infrastructure.adapter.in.api.v1;

import bank_mega.corsys.application.assembler.DocumentAssembler;
import bank_mega.corsys.application.common.dto.DeleteResponse;
import bank_mega.corsys.application.common.dto.PaginationResponse;
import bank_mega.corsys.application.common.dto.ReadListResponse;
import bank_mega.corsys.application.common.dto.ReadRetrieveResponse;
import bank_mega.corsys.application.document.command.*;
import bank_mega.corsys.application.document.dto.DocumentResponse;
import bank_mega.corsys.application.document.dto.OCRResponse;
import bank_mega.corsys.application.document.dto.UploadDocumentResponse;
import bank_mega.corsys.application.document.usecase.*;
import bank_mega.corsys.domain.model.common.Id;
import bank_mega.corsys.domain.model.document.Document;
import bank_mega.corsys.domain.model.user.User;
import bank_mega.corsys.infrastructure.util.ParserUtil;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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

    private final GetDocumentUseCase getDocumentUseCase;
    private final RetrieveDocumentUseCase retrieveDocumentUseCase;
    private final CreateDocumentUseCase createDocumentUseCase;
    private final CreateMultipleDocumentUseCase createMultipleDocumentUseCase;
    private final UpdateDocumentUseCase updateDocumentUseCase;
    private final SoftDeleteDocumentUseCase softDeleteDocumentUseCase;
    private final DeleteDocumentUseCase deleteDocumentUseCase;

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ReadListResponse<List<DocumentResponse>> list(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "perPage", required = false, defaultValue = "5") int perPage,
            @RequestParam(value = "filter", required = false) String filter,
            @RequestParam(value = "sort", required = false) String sort,
            @RequestParam(value = "expands", required = false) String expand
    ) {
        Page<@NonNull Document> pageable = this.getDocumentUseCase.execute(
                page,
                perPage,
                ParserUtil.expandParse(expand),
                sort,
                filter
        );
        List<DocumentResponse> data = pageable.stream()
                .map(domainEntity -> DocumentAssembler.toResponse(domainEntity, ParserUtil.expandParse(expand)))
                .toList();
        return ReadListResponse.<List<DocumentResponse>>builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(data)
                .pagination(
                        PaginationResponse.builder()
                                .currentPage(pageable.getNumber() + 1)
                                .totalPage(pageable.getTotalPages())
                                .perPage(pageable.getSize())
                                .total(pageable.getTotalElements())
                                .count(pageable.getNumberOfElements())
                                .hasNext(pageable.hasNext())
                                .hasPrevious(pageable.hasPrevious())
                                .hasContent(pageable.hasContent())
                                .build()
                )
                .build();
    }

    @GetMapping(
            path = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ReadRetrieveResponse<DocumentResponse> retrieve(
            @PathVariable @NotNull Long id,
            @RequestParam(value = "expands", required = false) String expand
    ) {
        Document data = this.retrieveDocumentUseCase.execute(id, ParserUtil.expandParse(expand));
        return ReadRetrieveResponse.<DocumentResponse>builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(DocumentAssembler.toResponse(data, ParserUtil.expandParse(expand)))
                .build();
    }

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

    @PostMapping(
            value = "/multiple",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ReadRetrieveResponse<List<DocumentResponse>> createMultiple(
            @AuthenticationPrincipal User authPrincipal,
            @RequestParam("files") List<MultipartFile> files
    ) throws Exception {

        var command = CreateDocumentMultipartMultipleCommand.builder()
                .files(files)
                .build();

        List<DocumentResponse> data =
                this.createMultipleDocumentUseCase.execute(command, authPrincipal);

        return ReadRetrieveResponse.<List<DocumentResponse>>builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(data)
                .build();
    }


    @PutMapping(
            path = "/{id}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ReadRetrieveResponse<DocumentResponse> update(
            @AuthenticationPrincipal User authPrincipal,
            @PathVariable @NotNull Long id,
            @RequestParam("file") MultipartFile file
    ) throws Exception {
        var command = UpdateDocumentMultipartCommand.builder()
                .id(id)
                .file(file)
                .build();

        DocumentResponse data = this.updateDocumentUseCase.execute(command, authPrincipal);
        return ReadRetrieveResponse.<DocumentResponse>builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(data)
                .build();
    }

    @DeleteMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ReadRetrieveResponse<DocumentResponse> softDelete(
            @AuthenticationPrincipal User authPrincipal,
            @Valid @RequestBody SoftDeleteDocumentCommand command
    ) {
        DocumentResponse data = this.softDeleteDocumentUseCase.execute(command, authPrincipal);
        return ReadRetrieveResponse.<DocumentResponse>builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(data)
                .build();
    }

    @DeleteMapping(
            path = "/{id}/destroy",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public DeleteResponse<Id> delete(@PathVariable @NotNull Long id) {
        Id pk = this.deleteDocumentUseCase.execute(id);
        return DeleteResponse.<Id>builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .id(pk)
                .build();
    }
}