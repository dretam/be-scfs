package bank_mega.corsys.infrastructure.adapter.in.api.v1;

import bank_mega.corsys.application.common.dto.DeleteResponse;
import bank_mega.corsys.application.common.dto.PaginationResponse;
import bank_mega.corsys.application.common.dto.ReadListResponse;
import bank_mega.corsys.application.common.dto.ReadRetrieveResponse;
import bank_mega.corsys.application.parameter.jenisnasabahpenerima.command.CreateParameterJenisNasabahPenerimaCommand;
import bank_mega.corsys.application.parameter.jenisnasabahpenerima.command.SoftDeleteParameterJenisNasabahPenerimaCommand;
import bank_mega.corsys.application.parameter.jenisnasabahpenerima.command.UpdateParameterJenisNasabahPenerimaCommand;
import bank_mega.corsys.application.parameter.jenisnasabahpenerima.dto.ParameterJenisNasabahPenerimaResponse;
import bank_mega.corsys.application.parameter.jenisnasabahpenerima.usecase.*;
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

@Tag(name = "Parameter Jenis Nasabah Penerima")
@RestController
@RequestMapping("/api/v1/parameters/jenis-nasabah-penerima")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authorization")
@RateLimiter(name = "global")
public class ParameterJenisNasabahPenerimaApi {

    private final PageParameterJenisNasabahPenerimaUseCase pageParameterJenisNasabahPenerimaUseCase;
    private final RetrieveParameterJenisNasabahPenerimaUseCase retrieveParameterJenisNasabahPenerimaUseCase;
    private final CreateParameterJenisNasabahPenerimaUseCase createParameterJenisNasabahPenerimaUseCase;
    private final UpdateParameterJenisNasabahPenerimaUseCase updateParameterJenisNasabahPenerimaUseCase;
    private final SoftDeleteParameterJenisNasabahPenerimaUseCase softDeleteParameterJenisNasabahPenerimaUseCase;
    private final DeleteParameterJenisNasabahPenerimaUseCase deleteParameterJenisNasabahPenerimaUseCase;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ReadListResponse<List<ParameterJenisNasabahPenerimaResponse>> list(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "perPage", required = false, defaultValue = "5") int perPage,
            @RequestParam(value = "filter", required = false) String filter,
            @RequestParam(value = "sort", required = false) String sort
    ) {
        Page<ParameterJenisNasabahPenerimaResponse> pageable = this.pageParameterJenisNasabahPenerimaUseCase.execute(page, perPage, sort, filter);
        List<ParameterJenisNasabahPenerimaResponse> data = pageable.getContent();
        return ReadListResponse.<List<ParameterJenisNasabahPenerimaResponse>>builder()
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
    public ReadRetrieveResponse<ParameterJenisNasabahPenerimaResponse> retrieve(@PathVariable Integer code) {
        ParameterJenisNasabahPenerimaResponse data = this.retrieveParameterJenisNasabahPenerimaUseCase.execute(code);
        return ReadRetrieveResponse.<ParameterJenisNasabahPenerimaResponse>builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(data)
                .build();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ReadRetrieveResponse<ParameterJenisNasabahPenerimaResponse> create(
            @AuthenticationPrincipal User authPrincipal,
            @RequestBody @Valid CreateParameterJenisNasabahPenerimaCommand command
    ) {
        ParameterJenisNasabahPenerimaResponse data = this.createParameterJenisNasabahPenerimaUseCase.execute(command, authPrincipal);
        return ReadRetrieveResponse.<ParameterJenisNasabahPenerimaResponse>builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(data)
                .build();
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ReadRetrieveResponse<ParameterJenisNasabahPenerimaResponse> update(
            @AuthenticationPrincipal User authPrincipal,
            @RequestBody @Valid UpdateParameterJenisNasabahPenerimaCommand command
    ) {
        ParameterJenisNasabahPenerimaResponse data = this.updateParameterJenisNasabahPenerimaUseCase.execute(command, authPrincipal);
        return ReadRetrieveResponse.<ParameterJenisNasabahPenerimaResponse>builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(data)
                .build();
    }

    @DeleteMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ReadRetrieveResponse<ParameterJenisNasabahPenerimaResponse> softDelete(
            @AuthenticationPrincipal User authPrincipal,
            @RequestBody @Valid SoftDeleteParameterJenisNasabahPenerimaCommand command
    ) {
        ParameterJenisNasabahPenerimaResponse data = this.softDeleteParameterJenisNasabahPenerimaUseCase.execute(command, authPrincipal);
        return ReadRetrieveResponse.<ParameterJenisNasabahPenerimaResponse>builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(data)
                .build();
    }

    @DeleteMapping(path = "/{code}/destroy", produces = MediaType.APPLICATION_JSON_VALUE)
    public DeleteResponse<Integer> delete(@PathVariable Integer code) {
        Integer pk = this.deleteParameterJenisNasabahPenerimaUseCase.execute(code).value();
        return DeleteResponse.<Integer>builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .id(pk)
                .build();
    }

}
