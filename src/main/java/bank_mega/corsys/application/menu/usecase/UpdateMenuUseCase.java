package bank_mega.corsys.application.menu.usecase;

import bank_mega.corsys.application.assembler.MenuAssembler;
import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.application.menu.command.UpdateMenuCommand;
import bank_mega.corsys.application.menu.dto.MenuResponse;
import bank_mega.corsys.domain.exception.DomainRuleViolationException;
import bank_mega.corsys.domain.exception.MenuNotFoundException;
import bank_mega.corsys.domain.model.menu.Menu;
import bank_mega.corsys.domain.model.menu.MenuCode;
import bank_mega.corsys.domain.model.menu.MenuId;
import bank_mega.corsys.domain.model.menu.MenuName;
import bank_mega.corsys.domain.model.user.User;
import bank_mega.corsys.domain.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
public class UpdateMenuUseCase {

    private final MenuRepository menuRepository;

    @Transactional
    public MenuResponse execute(UpdateMenuCommand command, User authPrincipal) {
        // Find existing menu
        Menu menu = menuRepository.findFirstByIdAndAuditDeletedAtIsNull(
                        new MenuId(command.id()))
                .orElseThrow(() -> new MenuNotFoundException(new MenuId(command.id())));

        // Validate code uniqueness if code is being updated
        command.code().ifPresent(newCode -> {
            menuRepository.findFirstByCode(new MenuCode(newCode))
                    .ifPresent(existing -> {
                        if (!existing.getId().equals(menu.getId())) {
                            throw new DomainRuleViolationException("Menu code already exists: " + newCode);
                        }
                    });
        });

        // Validate parent if provided
        command.parentId().ifPresent(parentId -> {
            if (parentId != null) {
                menuRepository.findFirstByIdAndAuditDeletedAtIsNull(new MenuId(parentId))
                        .orElseThrow(() -> new DomainRuleViolationException("Parent menu not found: " + parentId));
            }
        });

        // Update fields
        command.name().ifPresent(name -> menu.changeName(new MenuName(name)));
        command.code().ifPresent(code -> menu.changeCode(new MenuCode(code)));
        command.path().ifPresent(menu::changePath);
        command.icon().ifPresent(menu::changeIcon);
        command.parentId().ifPresent(parentId -> menu.changeParent(parentId != null ? new MenuId(parentId) : null));
        command.sortOrder().ifPresent(menu::changeSortOrder);

        // Update audit
        menu.updateAudit(authPrincipal.getId().value());

        // Save to repository
        Menu saved = menuRepository.save(menu);

        // Convert to response DTO
        return MenuAssembler.toResponse(saved);
    }

}
