package bank_mega.corsys.infrastructure.adapter.in.api.v1;

import bank_mega.corsys.application.assembler.MenuAssembler;
import bank_mega.corsys.application.common.dto.DeleteResponse;
import bank_mega.corsys.application.common.dto.PaginationResponse;
import bank_mega.corsys.application.common.dto.ReadListResponse;
import bank_mega.corsys.application.common.dto.ReadRetrieveResponse;
import bank_mega.corsys.application.menu.command.CreateMenuCommand;
import bank_mega.corsys.application.menu.command.SoftDeleteMenuCommand;
import bank_mega.corsys.application.menu.command.UpdateMenuCommand;
import bank_mega.corsys.application.menu.dto.MenuResponse;
import bank_mega.corsys.application.menu.usecase.*;
import bank_mega.corsys.domain.model.menu.Menu;
import bank_mega.corsys.domain.model.menu.MenuId;
import bank_mega.corsys.domain.model.user.User;
import bank_mega.corsys.infrastructure.config.security.HasPermission;
import bank_mega.corsys.infrastructure.util.ParserUtil;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Menus")
@RestController
@RequestMapping("/api/v1/menus")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authorization")
@RateLimiter(name = "global")
public class MenuApi {

    private final PageMenuUseCase pageMenuUseCase;
    private final RetrieveMenuUseCase retrieveMenuUseCase;
    private final CreateMenuUseCase createMenuUseCase;
    private final UpdateMenuUseCase updateMenuUseCase;
    private final SoftDeleteMenuUseCase softDeleteMenuUseCase;
    private final DeleteMenuUseCase deleteMenuUseCase;

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @HasPermission("MENU_READ")
    public ReadListResponse<List<MenuResponse>> list(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "perPage", required = false, defaultValue = "5") int perPage,
            @RequestParam(value = "filter", required = false) String filter,
            @RequestParam(value = "sort", required = false) String sort,
            @RequestParam(value = "expands", required = false) String expands
    ) {
        Page<@NonNull MenuResponse> pageable = this.pageMenuUseCase.execute(page, perPage, sort, filter, ParserUtil.expandParse(expands));
        List<MenuResponse> data = pageable.getContent();
        return ReadListResponse.<List<MenuResponse>>builder()
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
    @HasPermission("MENU_READ")
    public ReadRetrieveResponse<MenuResponse> retrieve(@PathVariable Long id) {
        MenuResponse data = this.retrieveMenuUseCase.execute(id);
        return ReadRetrieveResponse.<MenuResponse>builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(data)
                .build();
    }

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @HasPermission("MENU_CREATE")
    public ReadRetrieveResponse<MenuResponse> create(
            @AuthenticationPrincipal User authPrincipal,
            @RequestBody @Valid CreateMenuCommand command
    ) {
        MenuResponse data = this.createMenuUseCase.execute(command, authPrincipal);
        return ReadRetrieveResponse.<MenuResponse>builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(data)
                .build();
    }

    @PutMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @HasPermission("MENU_UPDATE")
    public ReadRetrieveResponse<MenuResponse> update(
            @AuthenticationPrincipal User authPrincipal,
            @RequestBody @Valid UpdateMenuCommand command
    ) {
        MenuResponse data = this.updateMenuUseCase.execute(command, authPrincipal);
        return ReadRetrieveResponse.<MenuResponse>builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(data)
                .build();
    }

    @DeleteMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @HasPermission("MENU_DELETE")
    public ReadRetrieveResponse<MenuResponse> softDelete(
            @AuthenticationPrincipal User authPrincipal,
            @RequestBody @Valid SoftDeleteMenuCommand command
    ) {
        MenuResponse data = this.softDeleteMenuUseCase.execute(command, authPrincipal);
        return ReadRetrieveResponse.<MenuResponse>builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(data)
                .build();
    }

    @DeleteMapping(
            path = "/{id}/destroy",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @HasPermission("MENU_DELETE")
    public DeleteResponse<MenuId> delete(@PathVariable Long id) {
        MenuId pk = this.deleteMenuUseCase.execute(id);
        return DeleteResponse.<MenuId>builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .id(pk)
                .build();
    }

}
