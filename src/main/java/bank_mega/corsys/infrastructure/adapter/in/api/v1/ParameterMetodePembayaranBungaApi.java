package bank_mega.corsys.infrastructure.adapter.in.api.v1;

import bank_mega.corsys.application.common.dto.DeleteResponse;
import bank_mega.corsys.application.common.dto.PaginationResponse;
import bank_mega.corsys.application.common.dto.ReadListResponse;
import bank_mega.corsys.application.common.dto.ReadRetrieveResponse;
import bank_mega.corsys.application.parameter.metodepembayaranbunga.command.CreateParameterMetodePembayaranBungaCommand;
import bank_mega.corsys.application.parameter.metodepembayaranbunga.command.SoftDeleteParameterMetodePembayaranBungaCommand;
import bank_mega.corsys.application.parameter.metodepembayaranbunga.command.UpdateParameterMetodePembayaranBungaCommand;
import bank_mega.corsys.application.parameter.metodepembayaranbunga.dto.ParameterMetodePembayaranBungaResponse;
import bank_mega.corsys.application.parameter.metodepembayaranbunga.usecase.*;
import bank_mega.corsys.domain.model.user.User;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Parameter Metode Pembayaran Bunga")
@RestController
@RequestMapping("/api/v1/parameters/metode-pembayaran-bunga")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authorization")
@RateLimiter(name = "global")
public class ParameterMetodePembayaranBungaApi {

    private final PageParameterMetodePembayaranBungaUseCase pageParameterMetodePembayaranBungaUseCase;
    private final RetrieveParameterMetodePembayaranBungaUseCase retrieveParameterMetodePembayaranBungaUseCase;
    private final CreateParameterMetodePembayaranBungaUseCase createParameterMetodePembayaranBungaUseCase;
    private final UpdateParameterMetodePembayaranBungaUseCase updateParameterMetodePembayaranBungaUseCase;
    private final SoftDeleteParameterMetodePembayaranBungaUseCase softDeleteParameterMetodePembayaranBungaUseCase;
    private final DeleteParameterMetodePembayaranBungaUseCase deleteParameterMetodePembayaranBungaUseCase;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ReadListResponse<List<ParameterMetodePembayaranBungaResponse>> list(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "perPage", required = false, defaultValue = "5") int perPage,
            @RequestParam(value = "filter", required = false) String filter,
            @RequestParam(value = "sort", required = false) String sort
    ) {
        Page<ParameterMetodePembayaranBungaResponse> pageable = this.pageParameterMetodePembayaranBungaUseCase.execute(page, perPage, sort, filter);
        List<ParameterMetodePembayaranBungaResponse> data = pageable.getContent();
        return ReadListResponse.<List<ParameterMetodePembayaranBungaResponse>>builder()
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

    @GetMapping(path = "/{code}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ReadRetrieveResponse<ParameterMetodePembayaranBungaResponse> retrieve(@PathVariable String code) {
        ParameterMetodePembayaranBungaResponse data = this.retrieveParameterMetodePembayaranBungaUseCase.execute(code);
        return ReadRetrieveResponse.<ParameterMetodePembayaranBungaResponse>builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(data)
                .build();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ReadRetrieveResponse<ParameterMetodePembayaranBungaResponse> create(
            @AuthenticationPrincipal User authPrincipal,
            @RequestBody @Valid CreateParameterMetodePembayaranBungaCommand command
    ) {
        ParameterMetodePembayaranBungaResponse data = this.createParameterMetodePembayaranBungaUseCase.execute(command, authPrincipal);
        return ReadRetrieveResponse.<ParameterMetodePembayaranBungaResponse>builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(data)
                .build();
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ReadRetrieveResponse<ParameterMetodePembayaranBungaResponse> update(
            @AuthenticationPrincipal User authPrincipal,
            @RequestBody @Valid UpdateParameterMetodePembayaranBungaCommand command
    ) {
        ParameterMetodePembayaranBungaResponse data = this.updateParameterMetodePembayaranBungaUseCase.execute(command, authPrincipal);
        return ReadRetrieveResponse.<ParameterMetodePembayaranBungaResponse>builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(data)
                .build();
    }

    @DeleteMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ReadRetrieveResponse<ParameterMetodePembayaranBungaResponse> softDelete(
            @AuthenticationPrincipal User authPrincipal,
            @RequestBody @Valid SoftDeleteParameterMetodePembayaranBungaCommand command
    ) {
        ParameterMetodePembayaranBungaResponse data = this.softDeleteParameterMetodePembayaranBungaUseCase.execute(command, authPrincipal);
        return ReadRetrieveResponse.<ParameterMetodePembayaranBungaResponse>builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(data)
                .build();
    }

    @DeleteMapping(path = "/{code}/destroy", produces = MediaType.APPLICATION_JSON_VALUE)
    public DeleteResponse<String> delete(@PathVariable String code) {
        String pk = this.deleteParameterMetodePembayaranBungaUseCase.execute(code).value();
        return DeleteResponse.<String>builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .id(pk)
                .build();
    }

}
