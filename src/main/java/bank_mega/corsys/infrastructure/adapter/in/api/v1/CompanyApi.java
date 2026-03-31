package bank_mega.corsys.infrastructure.adapter.in.api.v1;

import bank_mega.corsys.application.assembler.CompanyAssembler;
import bank_mega.corsys.application.common.dto.DeleteResponse;
import bank_mega.corsys.application.common.dto.PaginationResponse;
import bank_mega.corsys.application.common.dto.ReadListResponse;
import bank_mega.corsys.application.common.dto.ReadRetrieveResponse;
import bank_mega.corsys.application.company.command.CreateCompanyCommand;
import bank_mega.corsys.application.company.command.SoftDeleteCompanyCommand;
import bank_mega.corsys.application.company.command.UpdateCompanyCommand;
import bank_mega.corsys.application.company.dto.CompanyResponse;
import bank_mega.corsys.application.company.usecase.*;
import bank_mega.corsys.domain.model.company.Company;
import bank_mega.corsys.domain.model.company.CompanyId;
import bank_mega.corsys.domain.model.user.User;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "Companies")
@RestController
@RequestMapping("/api/v1/companies")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authorization")
@RateLimiter(name = "global")
public class CompanyApi {

    private final PageCompanyUseCase pageCompanyUseCase;
    private final RetrieveCompanyUseCase retrieveCompanyUseCase;
    private final CreateCompanyUseCase createCompanyUseCase;
    private final UpdateCompanyUseCase updateCompanyUseCase;
    private final SoftDeleteCompanyUseCase softDeleteCompanyUseCase;
    private final DeleteCompanyUseCase deleteCompanyUseCase;

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ReadListResponse<List<CompanyResponse>> list(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "perPage", required = false, defaultValue = "5") int perPage,
            @RequestParam(value = "filter", required = false) String filter,
            @RequestParam(value = "sort", required = false) String sort
    ) {
        Page<@NonNull Company> pageable = this.pageCompanyUseCase.execute(page, perPage, sort, filter);
        List<CompanyResponse> data = pageable.stream()
                .map(CompanyAssembler::toResponse)
                .toList();
        return ReadListResponse.<List<CompanyResponse>>builder()
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

    @GetMapping(
            path = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ReadRetrieveResponse<CompanyResponse> retrieve(@PathVariable UUID id) {
        CompanyResponse data = this.retrieveCompanyUseCase.execute(id);
        return ReadRetrieveResponse.<CompanyResponse>builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(data)
                .build();
    }

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ReadRetrieveResponse<CompanyResponse> create(
            @AuthenticationPrincipal User authPrincipal,
            @RequestBody CreateCompanyCommand command
    ) {
        CompanyResponse data = this.createCompanyUseCase.execute(command, authPrincipal);
        return ReadRetrieveResponse.<CompanyResponse>builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(data)
                .build();
    }

    @PutMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ReadRetrieveResponse<CompanyResponse> update(
            @AuthenticationPrincipal User authPrincipal,
            @RequestBody UpdateCompanyCommand command
    ) {
        CompanyResponse data = this.updateCompanyUseCase.execute(command, authPrincipal);
        return ReadRetrieveResponse.<CompanyResponse>builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(data)
                .build();
    }

    @DeleteMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ReadRetrieveResponse<CompanyResponse> softDelete(
            @AuthenticationPrincipal User authPrincipal,
            @RequestBody SoftDeleteCompanyCommand command
    ) {
        CompanyResponse data = this.softDeleteCompanyUseCase.execute(command, authPrincipal);
        return ReadRetrieveResponse.<CompanyResponse>builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(data)
                .build();
    }

    @DeleteMapping(
            path = "/{id}/destroy",
            // consumes = MediaType.APPLICATION_JSON_VALUE,
            consumes = "*/*",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public DeleteResponse<CompanyId> delete(@PathVariable UUID id) {
        CompanyId pk = this.deleteCompanyUseCase.execute(id);
        return DeleteResponse.<CompanyId>builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .id(pk)
                .build();
    }

}
