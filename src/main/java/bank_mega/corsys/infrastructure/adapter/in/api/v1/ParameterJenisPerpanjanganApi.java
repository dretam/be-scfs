package bank_mega.corsys.infrastructure.adapter.in.api.v1;

import bank_mega.corsys.application.common.dto.DeleteResponse;
import bank_mega.corsys.application.common.dto.PaginationResponse;
import bank_mega.corsys.application.common.dto.ReadListResponse;
import bank_mega.corsys.application.common.dto.ReadRetrieveResponse;
import bank_mega.corsys.application.parameter.jenisperpanjangan.command.CreateParameterJenisPerpanjanganCommand;
import bank_mega.corsys.application.parameter.jenisperpanjangan.command.SoftDeleteParameterJenisPerpanjanganCommand;
import bank_mega.corsys.application.parameter.jenisperpanjangan.command.UpdateParameterJenisPerpanjanganCommand;
import bank_mega.corsys.application.parameter.jenisperpanjangan.dto.ParameterJenisPerpanjanganResponse;
import bank_mega.corsys.application.parameter.jenisperpanjangan.usecase.*;
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

@Tag(name = "Parameter Jenis Perpanjangan")
@RestController
@RequestMapping("/api/v1/parameters/jenis-perpanjangan")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authorization")
@RateLimiter(name = "global")
public class ParameterJenisPerpanjanganApi {

    private final PageParameterJenisPerpanjanganUseCase pageParameterJenisPerpanjanganUseCase;
    private final RetrieveParameterJenisPerpanjanganUseCase retrieveParameterJenisPerpanjanganUseCase;
    private final CreateParameterJenisPerpanjanganUseCase createParameterJenisPerpanjanganUseCase;
    private final UpdateParameterJenisPerpanjanganUseCase updateParameterJenisPerpanjanganUseCase;
    private final SoftDeleteParameterJenisPerpanjanganUseCase softDeleteParameterJenisPerpanjanganUseCase;
    private final DeleteParameterJenisPerpanjanganUseCase deleteParameterJenisPerpanjanganUseCase;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ReadListResponse<List<ParameterJenisPerpanjanganResponse>> list(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "perPage", required = false, defaultValue = "5") int perPage,
            @RequestParam(value = "filter", required = false) String filter,
            @RequestParam(value = "sort", required = false) String sort
    ) {
        Page<ParameterJenisPerpanjanganResponse> pageable = this.pageParameterJenisPerpanjanganUseCase.execute(page, perPage, sort, filter);
        List<ParameterJenisPerpanjanganResponse> data = pageable.getContent();
        return ReadListResponse.<List<ParameterJenisPerpanjanganResponse>>builder()
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
    public ReadRetrieveResponse<ParameterJenisPerpanjanganResponse> retrieve(@PathVariable String code) {
        ParameterJenisPerpanjanganResponse data = this.retrieveParameterJenisPerpanjanganUseCase.execute(code);
        return ReadRetrieveResponse.<ParameterJenisPerpanjanganResponse>builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(data)
                .build();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ReadRetrieveResponse<ParameterJenisPerpanjanganResponse> create(
            @AuthenticationPrincipal User authPrincipal,
            @RequestBody @Valid CreateParameterJenisPerpanjanganCommand command
    ) {
        ParameterJenisPerpanjanganResponse data = this.createParameterJenisPerpanjanganUseCase.execute(command, authPrincipal);
        return ReadRetrieveResponse.<ParameterJenisPerpanjanganResponse>builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(data)
                .build();
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ReadRetrieveResponse<ParameterJenisPerpanjanganResponse> update(
            @AuthenticationPrincipal User authPrincipal,
            @RequestBody @Valid UpdateParameterJenisPerpanjanganCommand command
    ) {
        ParameterJenisPerpanjanganResponse data = this.updateParameterJenisPerpanjanganUseCase.execute(command, authPrincipal);
        return ReadRetrieveResponse.<ParameterJenisPerpanjanganResponse>builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(data)
                .build();
    }

    @DeleteMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ReadRetrieveResponse<ParameterJenisPerpanjanganResponse> softDelete(
            @AuthenticationPrincipal User authPrincipal,
            @RequestBody @Valid SoftDeleteParameterJenisPerpanjanganCommand command
    ) {
        ParameterJenisPerpanjanganResponse data = this.softDeleteParameterJenisPerpanjanganUseCase.execute(command, authPrincipal);
        return ReadRetrieveResponse.<ParameterJenisPerpanjanganResponse>builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(data)
                .build();
    }

    @DeleteMapping(path = "/{code}/destroy", produces = MediaType.APPLICATION_JSON_VALUE)
    public DeleteResponse<String> delete(@PathVariable String code) {
        String pk = this.deleteParameterJenisPerpanjanganUseCase.execute(code).value();
        return DeleteResponse.<String>builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .id(pk)
                .build();
    }

}
