package bank_mega.corsys.application.menu.usecase;

import bank_mega.corsys.application.assembler.MenuAssembler;
import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.application.menu.command.CreateMenuCommand;
import bank_mega.corsys.application.menu.dto.MenuResponse;
import bank_mega.corsys.domain.exception.DomainRuleViolationException;
import bank_mega.corsys.domain.model.common.AuditTrail;
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
public class CreateMenuUseCase {

    private final MenuRepository menuRepository;

    @Transactional
    public MenuResponse execute(CreateMenuCommand command, User authPrincipal) {
        // Validate menu code uniqueness
        menuRepository.findFirstByCode(new MenuCode(command.code()))
                .ifPresent(existing -> {
                    throw new DomainRuleViolationException("Menu code already exists: " + command.code());
                });

        // Validate parent if provided
        MenuId parentId = null;
        if (command.parentId() != null) {
            parentId = new MenuId(command.parentId());
            menuRepository.findFirstByIdAndAuditDeletedAtIsNull(parentId)
                    .orElseThrow(() -> new DomainRuleViolationException("Parent menu not found: " + command.parentId()));
        }

        // Create new menu
        Menu newMenu = new Menu(
                null,
                new MenuName(command.name()),
                new MenuCode(command.code()),
                command.path(),
                command.icon(),
                parentId,
                command.sortOrder(),
                AuditTrail.create(authPrincipal.getId().value())
        );

        // Save to repository
        Menu saved = menuRepository.save(newMenu);

        // Convert to response DTO
        return MenuAssembler.toResponse(saved);
    }

}
