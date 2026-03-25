package bank_mega.corsys.infrastructure.adapter.in.api.v1;

import bank_mega.corsys.application.common.dto.DeleteResponse;
import bank_mega.corsys.application.common.dto.PaginationResponse;
import bank_mega.corsys.application.common.dto.ReadListResponse;
import bank_mega.corsys.application.common.dto.ReadRetrieveResponse;
import bank_mega.corsys.application.parameter.jenistransaksiskn.command.CreateParameterJenisTransaksiSknCommand;
import bank_mega.corsys.application.parameter.jenistransaksiskn.command.SoftDeleteParameterJenisTransaksiSknCommand;
import bank_mega.corsys.application.parameter.jenistransaksiskn.command.UpdateParameterJenisTransaksiSknCommand;
import bank_mega.corsys.application.parameter.jenistransaksiskn.dto.ParameterJenisTransaksiSknResponse;
import bank_mega.corsys.application.parameter.jenistransaksiskn.usecase.*;
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

@Tag(name = "Parameter Jenis Transaksi SKN")
@RestController
@RequestMapping("/api/v1/parameters/jenis-transaksi-skn")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authorization")
@RateLimiter(name = "global")
public class ParameterJenisTransaksiSknApi {

    private final PageParameterJenisTransaksiSknUseCase pageParameterJenisTransaksiSknUseCase;
    private final RetrieveParameterJenisTransaksiSknUseCase retrieveParameterJenisTransaksiSknUseCase;
    private final CreateParameterJenisTransaksiSknUseCase createParameterJenisTransaksiSknUseCase;
    private final UpdateParameterJenisTransaksiSknUseCase updateParameterJenisTransaksiSknUseCase;
    private final SoftDeleteParameterJenisTransaksiSknUseCase softDeleteParameterJenisTransaksiSknUseCase;
    private final DeleteParameterJenisTransaksiSknUseCase deleteParameterJenisTransaksiSknUseCase;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ReadListResponse<List<ParameterJenisTransaksiSknResponse>> list(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "perPage", required = false, defaultValue = "5") int perPage,
            @RequestParam(value = "filter", required = false) String filter,
            @RequestParam(value = "sort", required = false) String sort
    ) {
        Page<ParameterJenisTransaksiSknResponse> pageable = this.pageParameterJenisTransaksiSknUseCase.execute(page, perPage, sort, filter);
        List<ParameterJenisTransaksiSknResponse> data = pageable.getContent();
        return ReadListResponse.<List<ParameterJenisTransaksiSknResponse>>builder()
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
    public ReadRetrieveResponse<ParameterJenisTransaksiSknResponse> retrieve(@PathVariable Integer code) {
        ParameterJenisTransaksiSknResponse data = this.retrieveParameterJenisTransaksiSknUseCase.execute(code);
        return ReadRetrieveResponse.<ParameterJenisTransaksiSknResponse>builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(data)
                .build();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ReadRetrieveResponse<ParameterJenisTransaksiSknResponse> create(
            @AuthenticationPrincipal User authPrincipal,
            @RequestBody @Valid CreateParameterJenisTransaksiSknCommand command
    ) {
        ParameterJenisTransaksiSknResponse data = this.createParameterJenisTransaksiSknUseCase.execute(command, authPrincipal);
        return ReadRetrieveResponse.<ParameterJenisTransaksiSknResponse>builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(data)
                .build();
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ReadRetrieveResponse<ParameterJenisTransaksiSknResponse> update(
            @AuthenticationPrincipal User authPrincipal,
            @RequestBody @Valid UpdateParameterJenisTransaksiSknCommand command
    ) {
        ParameterJenisTransaksiSknResponse data = this.updateParameterJenisTransaksiSknUseCase.execute(command, authPrincipal);
        return ReadRetrieveResponse.<ParameterJenisTransaksiSknResponse>builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(data)
                .build();
    }

    @DeleteMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ReadRetrieveResponse<ParameterJenisTransaksiSknResponse> softDelete(
            @AuthenticationPrincipal User authPrincipal,
            @RequestBody @Valid SoftDeleteParameterJenisTransaksiSknCommand command
    ) {
        ParameterJenisTransaksiSknResponse data = this.softDeleteParameterJenisTransaksiSknUseCase.execute(command, authPrincipal);
        return ReadRetrieveResponse.<ParameterJenisTransaksiSknResponse>builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(data)
                .build();
    }

    @DeleteMapping(path = "/{code}/destroy", produces = MediaType.APPLICATION_JSON_VALUE)
    public DeleteResponse<Integer> delete(@PathVariable Integer code) {
        Integer pk = this.deleteParameterJenisTransaksiSknUseCase.execute(code).value();
        return DeleteResponse.<Integer>builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .id(pk)
                .build();
    }

}
