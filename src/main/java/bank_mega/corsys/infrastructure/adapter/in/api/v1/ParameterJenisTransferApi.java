package bank_mega.corsys.infrastructure.adapter.in.api.v1;

import bank_mega.corsys.application.common.dto.DeleteResponse;
import bank_mega.corsys.application.common.dto.PaginationResponse;
import bank_mega.corsys.application.common.dto.ReadListResponse;
import bank_mega.corsys.application.common.dto.ReadRetrieveResponse;
import bank_mega.corsys.application.parameter.jenistransfer.command.CreateParameterJenisTransferCommand;
import bank_mega.corsys.application.parameter.jenistransfer.command.SoftDeleteParameterJenisTransferCommand;
import bank_mega.corsys.application.parameter.jenistransfer.command.UpdateParameterJenisTransferCommand;
import bank_mega.corsys.application.parameter.jenistransfer.dto.ParameterJenisTransferResponse;
import bank_mega.corsys.application.parameter.jenistransfer.usecase.*;
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

@Tag(name = "Parameter Jenis Transfer")
@RestController
@RequestMapping("/api/v1/parameters/jenis-transfer")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authorization")
@RateLimiter(name = "global")
public class ParameterJenisTransferApi {

    private final PageParameterJenisTransferUseCase pageParameterJenisTransferUseCase;
    private final RetrieveParameterJenisTransferUseCase retrieveParameterJenisTransferUseCase;
    private final CreateParameterJenisTransferUseCase createParameterJenisTransferUseCase;
    private final UpdateParameterJenisTransferUseCase updateParameterJenisTransferUseCase;
    private final SoftDeleteParameterJenisTransferUseCase softDeleteParameterJenisTransferUseCase;
    private final DeleteParameterJenisTransferUseCase deleteParameterJenisTransferUseCase;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ReadListResponse<List<ParameterJenisTransferResponse>> list(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "perPage", required = false, defaultValue = "5") int perPage,
            @RequestParam(value = "filter", required = false) String filter,
            @RequestParam(value = "sort", required = false) String sort
    ) {
        Page<ParameterJenisTransferResponse> pageable = this.pageParameterJenisTransferUseCase.execute(page, perPage, sort, filter);
        List<ParameterJenisTransferResponse> data = pageable.getContent();
        return ReadListResponse.<List<ParameterJenisTransferResponse>>builder()
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
    public ReadRetrieveResponse<ParameterJenisTransferResponse> retrieve(@PathVariable Integer code) {
        ParameterJenisTransferResponse data = this.retrieveParameterJenisTransferUseCase.execute(code);
        return ReadRetrieveResponse.<ParameterJenisTransferResponse>builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(data)
                .build();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ReadRetrieveResponse<ParameterJenisTransferResponse> create(
            @AuthenticationPrincipal User authPrincipal,
            @RequestBody @Valid CreateParameterJenisTransferCommand command
    ) {
        ParameterJenisTransferResponse data = this.createParameterJenisTransferUseCase.execute(command, authPrincipal);
        return ReadRetrieveResponse.<ParameterJenisTransferResponse>builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(data)
                .build();
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ReadRetrieveResponse<ParameterJenisTransferResponse> update(
            @AuthenticationPrincipal User authPrincipal,
            @RequestBody @Valid UpdateParameterJenisTransferCommand command
    ) {
        ParameterJenisTransferResponse data = this.updateParameterJenisTransferUseCase.execute(command, authPrincipal);
        return ReadRetrieveResponse.<ParameterJenisTransferResponse>builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(data)
                .build();
    }

    @DeleteMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ReadRetrieveResponse<ParameterJenisTransferResponse> softDelete(
            @AuthenticationPrincipal User authPrincipal,
            @RequestBody @Valid SoftDeleteParameterJenisTransferCommand command
    ) {
        ParameterJenisTransferResponse data = this.softDeleteParameterJenisTransferUseCase.execute(command, authPrincipal);
        return ReadRetrieveResponse.<ParameterJenisTransferResponse>builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(data)
                .build();
    }

    @DeleteMapping(path = "/{code}/destroy", produces = MediaType.APPLICATION_JSON_VALUE)
    public DeleteResponse<Integer> delete(@PathVariable Integer code) {
        Integer pk = this.deleteParameterJenisTransferUseCase.execute(code).value();
        return DeleteResponse.<Integer>builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .id(pk)
                .build();
    }

}
