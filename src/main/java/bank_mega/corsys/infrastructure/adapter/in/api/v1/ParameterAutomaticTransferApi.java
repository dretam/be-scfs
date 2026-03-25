package bank_mega.corsys.infrastructure.adapter.in.api.v1;

import bank_mega.corsys.application.common.dto.DeleteResponse;
import bank_mega.corsys.application.common.dto.PaginationResponse;
import bank_mega.corsys.application.common.dto.ReadListResponse;
import bank_mega.corsys.application.common.dto.ReadRetrieveResponse;
import bank_mega.corsys.application.parameter.automatictransfer.command.CreateParameterAutomaticTransferCommand;
import bank_mega.corsys.application.parameter.automatictransfer.command.SoftDeleteParameterAutomaticTransferCommand;
import bank_mega.corsys.application.parameter.automatictransfer.command.UpdateParameterAutomaticTransferCommand;
import bank_mega.corsys.application.parameter.automatictransfer.dto.ParameterAutomaticTransferResponse;
import bank_mega.corsys.application.parameter.automatictransfer.usecase.*;
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

@Tag(name = "Parameter Automatic Transfer")
@RestController
@RequestMapping("/api/v1/parameters/automatic-transfer")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authorization")
@RateLimiter(name = "global")
public class ParameterAutomaticTransferApi {

    private final PageParameterAutomaticTransferUseCase pageParameterAutomaticTransferUseCase;
    private final RetrieveParameterAutomaticTransferUseCase retrieveParameterAutomaticTransferUseCase;
    private final CreateParameterAutomaticTransferUseCase createParameterAutomaticTransferUseCase;
    private final UpdateParameterAutomaticTransferUseCase updateParameterAutomaticTransferUseCase;
    private final SoftDeleteParameterAutomaticTransferUseCase softDeleteParameterAutomaticTransferUseCase;
    private final DeleteParameterAutomaticTransferUseCase deleteParameterAutomaticTransferUseCase;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ReadListResponse<List<ParameterAutomaticTransferResponse>> list(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "perPage", required = false, defaultValue = "5") int perPage,
            @RequestParam(value = "filter", required = false) String filter,
            @RequestParam(value = "sort", required = false) String sort
    ) {
        Page<ParameterAutomaticTransferResponse> pageable = this.pageParameterAutomaticTransferUseCase.execute(page, perPage, sort, filter);
        List<ParameterAutomaticTransferResponse> data = pageable.getContent();
        return ReadListResponse.<List<ParameterAutomaticTransferResponse>>builder()
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
    public ReadRetrieveResponse<ParameterAutomaticTransferResponse> retrieve(@PathVariable String code) {
        ParameterAutomaticTransferResponse data = this.retrieveParameterAutomaticTransferUseCase.execute(code);
        return ReadRetrieveResponse.<ParameterAutomaticTransferResponse>builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(data)
                .build();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ReadRetrieveResponse<ParameterAutomaticTransferResponse> create(
            @AuthenticationPrincipal User authPrincipal,
            @RequestBody @Valid CreateParameterAutomaticTransferCommand command
    ) {
        ParameterAutomaticTransferResponse data = this.createParameterAutomaticTransferUseCase.execute(command, authPrincipal);
        return ReadRetrieveResponse.<ParameterAutomaticTransferResponse>builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(data)
                .build();
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ReadRetrieveResponse<ParameterAutomaticTransferResponse> update(
            @AuthenticationPrincipal User authPrincipal,
            @RequestBody @Valid UpdateParameterAutomaticTransferCommand command
    ) {
        ParameterAutomaticTransferResponse data = this.updateParameterAutomaticTransferUseCase.execute(command, authPrincipal);
        return ReadRetrieveResponse.<ParameterAutomaticTransferResponse>builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(data)
                .build();
    }

    @DeleteMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ReadRetrieveResponse<ParameterAutomaticTransferResponse> softDelete(
            @AuthenticationPrincipal User authPrincipal,
            @RequestBody @Valid SoftDeleteParameterAutomaticTransferCommand command
    ) {
        ParameterAutomaticTransferResponse data = this.softDeleteParameterAutomaticTransferUseCase.execute(command, authPrincipal);
        return ReadRetrieveResponse.<ParameterAutomaticTransferResponse>builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(data)
                .build();
    }

    @DeleteMapping(path = "/{code}/destroy", produces = MediaType.APPLICATION_JSON_VALUE)
    public DeleteResponse<String> delete(@PathVariable String code) {
        String pk = this.deleteParameterAutomaticTransferUseCase.execute(code).value();
        return DeleteResponse.<String>builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .id(pk)
                .build();
    }

}
