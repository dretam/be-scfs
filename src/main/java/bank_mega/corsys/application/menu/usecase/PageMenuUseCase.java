package bank_mega.corsys.application.menu.usecase;

import bank_mega.corsys.application.assembler.MenuAssembler;
import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.application.menu.dto.MenuResponse;
import bank_mega.corsys.domain.model.menu.Menu;
import bank_mega.corsys.domain.repository.MenuRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@UseCase
@RequiredArgsConstructor
public class PageMenuUseCase {

    private final MenuRepository menuRepository;

    @Transactional(readOnly = true)
    public Page<@NonNull MenuResponse> execute(int page, int perPage, String sort, String filter, Set<String> expands) {
        Page<@NonNull Menu> pageable = menuRepository.findAllPageable(page, perPage, sort, filter, expands);

        List<MenuResponse> content = pageable.stream()
                .map(MenuAssembler::toResponse)
                .toList();

        return new PageImpl<>(
                content,
                PageRequest.of(page - 1, perPage),
                pageable.getTotalElements()
        );
    }

}
