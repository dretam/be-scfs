package bank_mega.corsys.infrastructure.adapter.in.api.v1;

import bank_mega.corsys.application.assembler.UserAssembler;
import bank_mega.corsys.application.common.dto.PaginationResponse;
import bank_mega.corsys.application.user.command.*;
import bank_mega.corsys.application.user.dto.UserUploadResponse;
import bank_mega.corsys.domain.model.user.User;
import bank_mega.corsys.infrastructure.adapter.in.validation.user.UserIdExist;
import bank_mega.corsys.infrastructure.config.security.HasPermission;
import bank_mega.corsys.infrastructure.util.ParserUtil;
import bank_mega.corsys.application.common.dto.DeleteResponse;
import bank_mega.corsys.application.common.dto.ReadListResponse;
import bank_mega.corsys.application.common.dto.ReadRetrieveResponse;
import bank_mega.corsys.application.user.dto.UserResponse;
import bank_mega.corsys.application.user.usecase.*;
import bank_mega.corsys.domain.model.user.UserId;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;


@Tag(name = "Users")
@Validated
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authorization")
@RateLimiter(name = "global")
public class UserApi {

    private final PageUserUseCase pageUserUseCase;
    private final RetrieveUserUseCase retrieveUserUseCase;
    private final CreateUserUseCase createUserUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final SendTokenChangePassUserUseCase sendTokenChangePassUserUseCase;
    private final SoftDeleteUserUseCase softDeleteUserUseCase;
    private final DeleteUserUseCase deleteUserUseCase;
    private final UserAssembler userAssembler;

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @HasPermission("USER_READ")
    public ReadListResponse<List<UserResponse>> list(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "perPage", required = false, defaultValue = "5") int perPage,
            @RequestParam(value = "filter", required = false) String filter,
            @RequestParam(value = "sort", required = false) String sort,
            @RequestParam(value = "expands", required = false) String expand
    ) {
        Page<@NonNull User> pageable = this.pageUserUseCase.execute(
                page,
                perPage,
                ParserUtil.expandParse(expand),
                sort,
                filter
        );
        List<UserResponse> data = pageable.stream()
                .map(domainEntity -> userAssembler.toResponse(domainEntity, ParserUtil.expandParse(expand)))
                .toList();
        return ReadListResponse.<List<UserResponse>>builder()
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
            value = "/downloadUserExcelTemplate",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @HasPermission("USER_READ")
    public ReadRetrieveResponse<Boolean> downloadUserExcelTemplate(@AuthenticationPrincipal User authPrincipal, HttpServletResponse response) {
        Boolean result = createUserUseCase.prepareDataUserForDownloadExcelTemplate(authPrincipal, response);

        return ReadRetrieveResponse.<Boolean>builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(result)
                .build();
    }

    @GetMapping(
            path = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @HasPermission("USER_READ")
    public ReadRetrieveResponse<UserResponse> retrieve(
            @PathVariable @NotNull @UserIdExist UUID id,
            @RequestParam(value = "expands", required = false) String expand
    ) {
        User data = this.retrieveUserUseCase.execute(id, ParserUtil.expandParse(expand));
        return ReadRetrieveResponse.<UserResponse>builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(userAssembler.toResponse(data, ParserUtil.expandParse(expand)))
                .build();
    }

    @PostMapping(
            value = "/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @HasPermission("USER_CREATE")
    public ReadRetrieveResponse<UserUploadResponse> uploadUsers(
            @RequestParam("file") MultipartFile file,
            @AuthenticationPrincipal User authPrincipal
    ) {
        UserUploadResponse result = createUserUseCase.uploadUsers(file, authPrincipal);
        return ReadRetrieveResponse.<UserUploadResponse>builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(userAssembler.toResponseUserUpload(result))
                .build();
    }

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @HasPermission("USER_CREATE")
    public ReadRetrieveResponse<UserResponse> create(
            @AuthenticationPrincipal User authPrincipal,
            @Valid @RequestBody CreateUserCommand command
    ) {
        UserResponse data = this.createUserUseCase.execute(command, authPrincipal);
        return ReadRetrieveResponse.<UserResponse>builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(data)
                .build();
    }

    @PostMapping(
            value = "sendTokenChangePass",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ReadRetrieveResponse<Boolean> sendTokenChangePass(
            @Valid @RequestBody SendTokenChangePassUserCommand command
    ) {
        Boolean result = this.sendTokenChangePassUserUseCase.sendTokenChangePassUserViaEmail(command);
        return ReadRetrieveResponse.<Boolean>builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(result)
                .build();
    }

    @PutMapping(
            value = "changePass",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ReadRetrieveResponse<UserResponse> changePas(
            @Valid @RequestBody ChangePassUserCommand command
    ) {
        UserResponse data = this.updateUserUseCase.executeChangePassword(command);
        return ReadRetrieveResponse.<UserResponse>builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(data)
                .build();
    }

    @PutMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @HasPermission("USER_UPDATE")
    public ReadRetrieveResponse<UserResponse> update(
            @AuthenticationPrincipal User authPrincipal,
            @Valid @RequestBody UpdateUserCommand command
    ) {
        UserResponse data = this.updateUserUseCase.execute(command, authPrincipal);
        return ReadRetrieveResponse.<UserResponse>builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(data)
                .build();
    }

    @DeleteMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @HasPermission("USER_DELETE")
    public ReadRetrieveResponse<UserResponse> softDelete(
            @AuthenticationPrincipal User authPrincipal,
            @Valid @RequestBody SoftDeleteUserCommand command
    ) {
        UserResponse data = this.softDeleteUserUseCase.execute(command, authPrincipal);
        return ReadRetrieveResponse.<UserResponse>builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(data)
                .build();
    }

    @DeleteMapping(
            path = "/{id}/destroy",
            // consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @HasPermission("USER_DELETE")
    public DeleteResponse<UserId> delete(@PathVariable @NotNull @UserIdExist UUID id) {
        UserId pk = this.deleteUserUseCase.execute(id);
        return DeleteResponse.<UserId>builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .id(pk)
                .build();
    }

}
