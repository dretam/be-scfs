package bank_mega.corsys.infrastructure.adapter.in.api.v1;

import bank_mega.corsys.application.common.dto.DeleteResponse;
import bank_mega.corsys.application.common.dto.PaginationResponse;
import bank_mega.corsys.application.common.dto.ReadListResponse;
import bank_mega.corsys.application.common.dto.ReadRetrieveResponse;
import bank_mega.corsys.application.parameter.metodepembayaranpokok.command.CreateParameterMetodePembayaranPokokCommand;
import bank_mega.corsys.application.parameter.metodepembayaranpokok.command.SoftDeleteParameterMetodePembayaranPokokCommand;
import bank_mega.corsys.application.parameter.metodepembayaranpokok.command.UpdateParameterMetodePembayaranPokokCommand;
import bank_mega.corsys.application.parameter.metodepembayaranpokok.dto.ParameterMetodePembayaranPokokResponse;
import bank_mega.corsys.application.parameter.metodepembayaranpokok.usecase.*;
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

@Tag(name = "Parameter Metode Pembayaran Pokok")
@RestController
@RequestMapping("/api/v1/parameters/metode-pembayaran-pokok")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authorization")
@RateLimiter(name = "global")
public class ParameterMetodePembayaranPokokApi {

    private final PageParameterMetodePembayaranPokokUseCase pageParameterMetodePembayaranPokokUseCase;
    private final RetrieveParameterMetodePembayaranPokokUseCase retrieveParameterMetodePembayaranPokokUseCase;
    private final CreateParameterMetodePembayaranPokokUseCase createParameterMetodePembayaranPokokUseCase;
    private final UpdateParameterMetodePembayaranPokokUseCase updateParameterMetodePembayaranPokokUseCase;
    private final SoftDeleteParameterMetodePembayaranPokokUseCase softDeleteParameterMetodePembayaranPokokUseCase;
    private final DeleteParameterMetodePembayaranPokokUseCase deleteParameterMetodePembayaranPokokUseCase;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ReadListResponse<List<ParameterMetodePembayaranPokokResponse>> list(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "perPage", required = false, defaultValue = "5") int perPage,
            @RequestParam(value = "filter", required = false) String filter,
            @RequestParam(value = "sort", required = false) String sort
    ) {
        Page<ParameterMetodePembayaranPokokResponse> pageable = this.pageParameterMetodePembayaranPokokUseCase.execute(page, perPage, sort, filter);
        List<ParameterMetodePembayaranPokokResponse> data = pageable.getContent();
        return ReadListResponse.<List<ParameterMetodePembayaranPokokResponse>>builder()
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
    public ReadRetrieveResponse<ParameterMetodePembayaranPokokResponse> retrieve(@PathVariable String code) {
        ParameterMetodePembayaranPokokResponse data = this.retrieveParameterMetodePembayaranPokokUseCase.execute(code);
        return ReadRetrieveResponse.<ParameterMetodePembayaranPokokResponse>builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(data)
                .build();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ReadRetrieveResponse<ParameterMetodePembayaranPokokResponse> create(
            @AuthenticationPrincipal User authPrincipal,
            @RequestBody @Valid CreateParameterMetodePembayaranPokokCommand command
    ) {
        ParameterMetodePembayaranPokokResponse data = this.createParameterMetodePembayaranPokokUseCase.execute(command, authPrincipal);
        return ReadRetrieveResponse.<ParameterMetodePembayaranPokokResponse>builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(data)
                .build();
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ReadRetrieveResponse<ParameterMetodePembayaranPokokResponse> update(
            @AuthenticationPrincipal User authPrincipal,
            @RequestBody @Valid UpdateParameterMetodePembayaranPokokCommand command
    ) {
        ParameterMetodePembayaranPokokResponse data = this.updateParameterMetodePembayaranPokokUseCase.execute(command, authPrincipal);
        return ReadRetrieveResponse.<ParameterMetodePembayaranPokokResponse>builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(data)
                .build();
    }

    @DeleteMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ReadRetrieveResponse<ParameterMetodePembayaranPokokResponse> softDelete(
            @AuthenticationPrincipal User authPrincipal,
            @RequestBody @Valid SoftDeleteParameterMetodePembayaranPokokCommand command
    ) {
        ParameterMetodePembayaranPokokResponse data = this.softDeleteParameterMetodePembayaranPokokUseCase.execute(command, authPrincipal);
        return ReadRetrieveResponse.<ParameterMetodePembayaranPokokResponse>builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(data)
                .build();
    }

    @DeleteMapping(path = "/{code}/destroy", produces = MediaType.APPLICATION_JSON_VALUE)
    public DeleteResponse<String> delete(@PathVariable String code) {
        String pk = this.deleteParameterMetodePembayaranPokokUseCase.execute(code).value();
        return DeleteResponse.<String>builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .id(pk)
                .build();
    }

}
