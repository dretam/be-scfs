package bank_mega.corsys.infrastructure.adapter.in.api.v1;

import bank_mega.corsys.application.assembler.OCRAssembler;
import bank_mega.corsys.application.common.dto.DeleteResponse;
import bank_mega.corsys.application.common.dto.PaginationResponse;
import bank_mega.corsys.application.common.dto.ReadListResponse;
import bank_mega.corsys.application.common.dto.ReadRetrieveResponse;
import bank_mega.corsys.application.document.dto.OCRResponse;
import bank_mega.corsys.application.ocr.command.ApproveOCRDataCommand;
import bank_mega.corsys.application.ocr.command.RejectOCRDataCommand;
import bank_mega.corsys.application.ocr.command.UpdateOCRDataCommand;
import bank_mega.corsys.application.ocr.usecase.ApproveOCRDataUseCase;
import bank_mega.corsys.application.ocr.usecase.DeleteOCRDataUseCase;
import bank_mega.corsys.application.ocr.usecase.GetOCRDataUseCase;
import bank_mega.corsys.application.ocr.usecase.RejectOCRDataUseCase;
import bank_mega.corsys.application.ocr.usecase.RetrieveOCRDataUseCase;
import bank_mega.corsys.application.ocr.usecase.UpdateOCRDataUseCase;
import bank_mega.corsys.domain.model.ocr.OCRData;
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

import java.util.List;
import java.util.Set;

@Tag(name = "OCR Data")
@Validated
@RestController
@RequestMapping("/api/v1/ocr-data")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authorization")
@RateLimiter(name = "global")
public class OCRDataApi {

    private final GetOCRDataUseCase getOCRDataUseCase;
    private final RetrieveOCRDataUseCase retrieveOCRDataUseCase;
    private final UpdateOCRDataUseCase updateOCRDataUseCase;
    private final DeleteOCRDataUseCase deleteOCRDataUseCase;
    private final ApproveOCRDataUseCase approveOCRDataUseCase;
    private final RejectOCRDataUseCase rejectOCRDataUseCase;

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ReadListResponse<List<OCRResponse>> list(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "perPage", required = false, defaultValue = "5") int perPage,
            @RequestParam(value = "filter", required = false) String filter,
            @RequestParam(value = "sort", required = false) String sort,
            @RequestParam(value = "expands", required = false) String expand
    ) {
        Page<@NonNull OCRData> pageable = this.getOCRDataUseCase.execute(
                page,
                perPage,
                ParserUtil.expandParse(expand),
                sort,
                filter
        );
        List<OCRResponse> data = pageable.stream()
                .map(domainEntity -> OCRAssembler.toResponse(domainEntity, ParserUtil.expandParse(expand)))
                .toList();
        return ReadListResponse.<List<OCRResponse>>builder()
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
    public ReadRetrieveResponse<OCRResponse> retrieve(
            @PathVariable @NotNull Long id,
            @RequestParam(value = "expands", required = false) String expand
    ) {
        OCRResponse data = this.retrieveOCRDataUseCase.execute(id, ParserUtil.expandParse(expand));
        return ReadRetrieveResponse.<OCRResponse>builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(data)
                .build();
    }

    @PutMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ReadRetrieveResponse<OCRResponse> update(
            @AuthenticationPrincipal User authPrincipal,
            @RequestBody @Valid UpdateOCRDataCommand command
    ) {
        OCRResponse data = this.updateOCRDataUseCase.execute(command, authPrincipal);
        return ReadRetrieveResponse.<OCRResponse>builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(data)
                .build();
    }

    @PostMapping(
            path = "/approve",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ReadListResponse<List<OCRResponse>> approve(
            @AuthenticationPrincipal User authPrincipal,
            @RequestBody @Valid ApproveOCRDataCommand command
    ) {
        List<OCRResponse> data = this.approveOCRDataUseCase.execute(command, authPrincipal);
        return ReadListResponse.<List<OCRResponse>>builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(data)
                .build();
    }

    @PostMapping(
            path = "/reject",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ReadListResponse<List<OCRResponse>> reject(
            @AuthenticationPrincipal User authPrincipal,
            @RequestBody @Valid RejectOCRDataCommand command
    ) {
        List<OCRResponse> data = this.rejectOCRDataUseCase.execute(command, authPrincipal);
        return ReadListResponse.<List<OCRResponse>>builder()
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
    public DeleteResponse<bank_mega.corsys.domain.model.common.Id> delete(
            @PathVariable @NotNull Long id
    ) {
        bank_mega.corsys.domain.model.common.Id pk = this.deleteOCRDataUseCase.execute(id);
        return DeleteResponse.<bank_mega.corsys.domain.model.common.Id>builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .id(pk)
                .build();
    }
}
