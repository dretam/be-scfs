package bank_mega.corsys.infrastructure.adapter.in.api.v1;

import bank_mega.corsys.application.common.dto.DeleteResponse;
import bank_mega.corsys.application.common.dto.PaginationResponse;
import bank_mega.corsys.application.common.dto.ReadListResponse;
import bank_mega.corsys.application.common.dto.ReadRetrieveResponse;
import bank_mega.corsys.application.parameter.sumberdana.command.CreateParameterSumberDanaCommand;
import bank_mega.corsys.application.parameter.sumberdana.command.SoftDeleteParameterSumberDanaCommand;
import bank_mega.corsys.application.parameter.sumberdana.command.UpdateParameterSumberDanaCommand;
import bank_mega.corsys.application.parameter.sumberdana.dto.ParameterSumberDanaResponse;
import bank_mega.corsys.application.parameter.sumberdana.usecase.*;
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

@Tag(name = "Parameter Sumber Dana")
@RestController
@RequestMapping("/api/v1/parameters/sumber-dana")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authorization")
@RateLimiter(name = "global")
public class ParameterSumberDanaApi {

    private final PageParameterSumberDanaUseCase pageParameterSumberDanaUseCase;
    private final RetrieveParameterSumberDanaUseCase retrieveParameterSumberDanaUseCase;
    private final CreateParameterSumberDanaUseCase createParameterSumberDanaUseCase;
    private final UpdateParameterSumberDanaUseCase updateParameterSumberDanaUseCase;
    private final SoftDeleteParameterSumberDanaUseCase softDeleteParameterSumberDanaUseCase;
    private final DeleteParameterSumberDanaUseCase deleteParameterSumberDanaUseCase;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ReadListResponse<List<ParameterSumberDanaResponse>> list(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "perPage", required = false, defaultValue = "5") int perPage,
            @RequestParam(value = "filter", required = false) String filter,
            @RequestParam(value = "sort", required = false) String sort
    ) {
        Page<ParameterSumberDanaResponse> pageable = this.pageParameterSumberDanaUseCase.execute(page, perPage, sort, filter);
        List<ParameterSumberDanaResponse> data = pageable.getContent();
        return ReadListResponse.<List<ParameterSumberDanaResponse>>builder()
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
    public ReadRetrieveResponse<ParameterSumberDanaResponse> retrieve(@PathVariable String code) {
        ParameterSumberDanaResponse data = this.retrieveParameterSumberDanaUseCase.execute(code);
        return ReadRetrieveResponse.<ParameterSumberDanaResponse>builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(data)
                .build();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ReadRetrieveResponse<ParameterSumberDanaResponse> create(
            @AuthenticationPrincipal User authPrincipal,
            @RequestBody @Valid CreateParameterSumberDanaCommand command
    ) {
        ParameterSumberDanaResponse data = this.createParameterSumberDanaUseCase.execute(command, authPrincipal);
        return ReadRetrieveResponse.<ParameterSumberDanaResponse>builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(data)
                .build();
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ReadRetrieveResponse<ParameterSumberDanaResponse> update(
            @AuthenticationPrincipal User authPrincipal,
            @RequestBody @Valid UpdateParameterSumberDanaCommand command
    ) {
        ParameterSumberDanaResponse data = this.updateParameterSumberDanaUseCase.execute(command, authPrincipal);
        return ReadRetrieveResponse.<ParameterSumberDanaResponse>builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(data)
                .build();
    }

    @DeleteMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ReadRetrieveResponse<ParameterSumberDanaResponse> softDelete(
            @AuthenticationPrincipal User authPrincipal,
            @RequestBody @Valid SoftDeleteParameterSumberDanaCommand command
    ) {
        ParameterSumberDanaResponse data = this.softDeleteParameterSumberDanaUseCase.execute(command, authPrincipal);
        return ReadRetrieveResponse.<ParameterSumberDanaResponse>builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(data)
                .build();
    }

    @DeleteMapping(path = "/{code}/destroy", produces = MediaType.APPLICATION_JSON_VALUE)
    public DeleteResponse<String> delete(@PathVariable String code) {
        String pk = this.deleteParameterSumberDanaUseCase.execute(code).value();
        return DeleteResponse.<String>builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .id(pk)
                .build();
    }

}
