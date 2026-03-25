package bank_mega.corsys.infrastructure.adapter.in.api.v1;

import bank_mega.corsys.application.common.dto.DeleteResponse;
import bank_mega.corsys.application.common.dto.PaginationResponse;
import bank_mega.corsys.application.common.dto.ReadListResponse;
import bank_mega.corsys.application.common.dto.ReadRetrieveResponse;
import bank_mega.corsys.application.parameter.statuskependudukanpenerima.command.CreateParameterStatusKependudukanPenerimaCommand;
import bank_mega.corsys.application.parameter.statuskependudukanpenerima.command.SoftDeleteParameterStatusKependudukanPenerimaCommand;
import bank_mega.corsys.application.parameter.statuskependudukanpenerima.command.UpdateParameterStatusKependudukanPenerimaCommand;
import bank_mega.corsys.application.parameter.statuskependudukanpenerima.dto.ParameterStatusKependudukanPenerimaResponse;
import bank_mega.corsys.application.parameter.statuskependudukanpenerima.usecase.*;
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

@Tag(name = "Parameter Status Kependudukan Penerima")
@RestController
@RequestMapping("/api/v1/parameters/status-kependudukan-penerima")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authorization")
@RateLimiter(name = "global")
public class ParameterStatusKependudukanPenerimaApi {

    private final PageParameterStatusKependudukanPenerimaUseCase pageParameterStatusKependudukanPenerimaUseCase;
    private final RetrieveParameterStatusKependudukanPenerimaUseCase retrieveParameterStatusKependudukanPenerimaUseCase;
    private final CreateParameterStatusKependudukanPenerimaUseCase createParameterStatusKependudukanPenerimaUseCase;
    private final UpdateParameterStatusKependudukanPenerimaUseCase updateParameterStatusKependudukanPenerimaUseCase;
    private final SoftDeleteParameterStatusKependudukanPenerimaUseCase softDeleteParameterStatusKependudukanPenerimaUseCase;
    private final DeleteParameterStatusKependudukanPenerimaUseCase deleteParameterStatusKependudukanPenerimaUseCase;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ReadListResponse<List<ParameterStatusKependudukanPenerimaResponse>> list(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "perPage", required = false, defaultValue = "5") int perPage,
            @RequestParam(value = "filter", required = false) String filter,
            @RequestParam(value = "sort", required = false) String sort
    ) {
        Page<ParameterStatusKependudukanPenerimaResponse> pageable = this.pageParameterStatusKependudukanPenerimaUseCase.execute(page, perPage, sort, filter);
        List<ParameterStatusKependudukanPenerimaResponse> data = pageable.getContent();
        return ReadListResponse.<List<ParameterStatusKependudukanPenerimaResponse>>builder()
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
    public ReadRetrieveResponse<ParameterStatusKependudukanPenerimaResponse> retrieve(@PathVariable Integer code) {
        ParameterStatusKependudukanPenerimaResponse data = this.retrieveParameterStatusKependudukanPenerimaUseCase.execute(code);
        return ReadRetrieveResponse.<ParameterStatusKependudukanPenerimaResponse>builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(data)
                .build();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ReadRetrieveResponse<ParameterStatusKependudukanPenerimaResponse> create(
            @AuthenticationPrincipal User authPrincipal,
            @RequestBody @Valid CreateParameterStatusKependudukanPenerimaCommand command
    ) {
        ParameterStatusKependudukanPenerimaResponse data = this.createParameterStatusKependudukanPenerimaUseCase.execute(command, authPrincipal);
        return ReadRetrieveResponse.<ParameterStatusKependudukanPenerimaResponse>builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(data)
                .build();
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ReadRetrieveResponse<ParameterStatusKependudukanPenerimaResponse> update(
            @AuthenticationPrincipal User authPrincipal,
            @RequestBody @Valid UpdateParameterStatusKependudukanPenerimaCommand command
    ) {
        ParameterStatusKependudukanPenerimaResponse data = this.updateParameterStatusKependudukanPenerimaUseCase.execute(command, authPrincipal);
        return ReadRetrieveResponse.<ParameterStatusKependudukanPenerimaResponse>builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(data)
                .build();
    }

    @DeleteMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ReadRetrieveResponse<ParameterStatusKependudukanPenerimaResponse> softDelete(
            @AuthenticationPrincipal User authPrincipal,
            @RequestBody @Valid SoftDeleteParameterStatusKependudukanPenerimaCommand command
    ) {
        ParameterStatusKependudukanPenerimaResponse data = this.softDeleteParameterStatusKependudukanPenerimaUseCase.execute(command, authPrincipal);
        return ReadRetrieveResponse.<ParameterStatusKependudukanPenerimaResponse>builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(data)
                .build();
    }

    @DeleteMapping(path = "/{code}/destroy", produces = MediaType.APPLICATION_JSON_VALUE)
    public DeleteResponse<Integer> delete(@PathVariable Integer code) {
        Integer pk = this.deleteParameterStatusKependudukanPenerimaUseCase.execute(code).value();
        return DeleteResponse.<Integer>builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .id(pk)
                .build();
    }

}
