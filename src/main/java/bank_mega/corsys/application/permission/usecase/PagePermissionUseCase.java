package bank_mega.corsys.application.permission.usecase;

import bank_mega.corsys.application.assembler.PermissionAssembler;
import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.application.permission.dto.PermissionResponse;
import bank_mega.corsys.domain.model.permission.Permission;
import bank_mega.corsys.domain.repository.PermissionRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@UseCase
@RequiredArgsConstructor
public class PagePermissionUseCase {

    private final PermissionRepository permissionRepository;

    @Transactional(readOnly = true)
    public Page<@NonNull PermissionResponse> execute(int page, int perPage, String sort, String filter) {
        Page<@NonNull Permission> pageable = permissionRepository.findAllPageable(page, perPage, sort, filter);

        List<PermissionResponse> content = pageable.stream()
                .map(PermissionAssembler::toResponse)
                .toList();

        return new PageImpl<>(
                content,
                PageRequest.of(page - 1, perPage),
                pageable.getTotalElements()
        );
    }

}
