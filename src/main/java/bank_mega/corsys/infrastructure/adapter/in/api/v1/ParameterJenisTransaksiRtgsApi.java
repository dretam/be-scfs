package bank_mega.corsys.infrastructure.adapter.in.api.v1;

import bank_mega.corsys.application.common.dto.DeleteResponse;
import bank_mega.corsys.application.common.dto.PaginationResponse;
import bank_mega.corsys.application.common.dto.ReadListResponse;
import bank_mega.corsys.application.common.dto.ReadRetrieveResponse;
import bank_mega.corsys.application.parameter.jenistransaksirtgs.command.CreateParameterJenisTransaksiRtgsCommand;
import bank_mega.corsys.application.parameter.jenistransaksirtgs.command.SoftDeleteParameterJenisTransaksiRtgsCommand;
import bank_mega.corsys.application.parameter.jenistransaksirtgs.command.UpdateParameterJenisTransaksiRtgsCommand;
import bank_mega.corsys.application.parameter.jenistransaksirtgs.dto.ParameterJenisTransaksiRtgsResponse;
import bank_mega.corsys.application.parameter.jenistransaksirtgs.usecase.*;
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

@Tag(name = "Parameter Jenis Transaksi RTGS")
@RestController
@RequestMapping("/api/v1/parameters/jenis-transaksi-rtgs")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authorization")
@RateLimiter(name = "global")
public class ParameterJenisTransaksiRtgsApi {

    private final PageParameterJenisTransaksiRtgsUseCase pageParameterJenisTransaksiRtgsUseCase;
    private final RetrieveParameterJenisTransaksiRtgsUseCase retrieveParameterJenisTransaksiRtgsUseCase;
    private final CreateParameterJenisTransaksiRtgsUseCase createParameterJenisTransaksiRtgsUseCase;
    private final UpdateParameterJenisTransaksiRtgsUseCase updateParameterJenisTransaksiRtgsUseCase;
    private final SoftDeleteParameterJenisTransaksiRtgsUseCase softDeleteParameterJenisTransaksiRtgsUseCase;
    private final DeleteParameterJenisTransaksiRtgsUseCase deleteParameterJenisTransaksiRtgsUseCase;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ReadListResponse<List<ParameterJenisTransaksiRtgsResponse>> list(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "perPage", required = false, defaultValue = "5") int perPage,
            @RequestParam(value = "filter", required = false) String filter,
            @RequestParam(value = "sort", required = false) String sort
    ) {
        Page<ParameterJenisTransaksiRtgsResponse> pageable = this.pageParameterJenisTransaksiRtgsUseCase.execute(page, perPage, sort, filter);
        List<ParameterJenisTransaksiRtgsResponse> data = pageable.getContent();
        return ReadListResponse.<List<ParameterJenisTransaksiRtgsResponse>>builder()
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
    public ReadRetrieveResponse<ParameterJenisTransaksiRtgsResponse> retrieve(@PathVariable Integer code) {
        ParameterJenisTransaksiRtgsResponse data = this.retrieveParameterJenisTransaksiRtgsUseCase.execute(code);
        return ReadRetrieveResponse.<ParameterJenisTransaksiRtgsResponse>builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(data)
                .build();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ReadRetrieveResponse<ParameterJenisTransaksiRtgsResponse> create(
            @AuthenticationPrincipal User authPrincipal,
            @RequestBody @Valid CreateParameterJenisTransaksiRtgsCommand command
    ) {
        ParameterJenisTransaksiRtgsResponse data = this.createParameterJenisTransaksiRtgsUseCase.execute(command, authPrincipal);
        return ReadRetrieveResponse.<ParameterJenisTransaksiRtgsResponse>builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(data)
                .build();
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ReadRetrieveResponse<ParameterJenisTransaksiRtgsResponse> update(
            @AuthenticationPrincipal User authPrincipal,
            @RequestBody @Valid UpdateParameterJenisTransaksiRtgsCommand command
    ) {
        ParameterJenisTransaksiRtgsResponse data = this.updateParameterJenisTransaksiRtgsUseCase.execute(command, authPrincipal);
        return ReadRetrieveResponse.<ParameterJenisTransaksiRtgsResponse>builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(data)
                .build();
    }

    @DeleteMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ReadRetrieveResponse<ParameterJenisTransaksiRtgsResponse> softDelete(
            @AuthenticationPrincipal User authPrincipal,
            @RequestBody @Valid SoftDeleteParameterJenisTransaksiRtgsCommand command
    ) {
        ParameterJenisTransaksiRtgsResponse data = this.softDeleteParameterJenisTransaksiRtgsUseCase.execute(command, authPrincipal);
        return ReadRetrieveResponse.<ParameterJenisTransaksiRtgsResponse>builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(data)
                .build();
    }

    @DeleteMapping(path = "/{code}/destroy", produces = MediaType.APPLICATION_JSON_VALUE)
    public DeleteResponse<Integer> delete(@PathVariable Integer code) {
        Integer pk = this.deleteParameterJenisTransaksiRtgsUseCase.execute(code).value();
        return DeleteResponse.<Integer>builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .id(pk)
                .build();
    }

}
