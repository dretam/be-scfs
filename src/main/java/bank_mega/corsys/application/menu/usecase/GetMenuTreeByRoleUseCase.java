package bank_mega.corsys.application.menu.usecase;

import bank_mega.corsys.application.assembler.MenuAssembler;
import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.application.menu.dto.MenuResponse;
import bank_mega.corsys.domain.model.menu.Menu;
import bank_mega.corsys.domain.model.role.RoleId;
import bank_mega.corsys.domain.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Use case for retrieving hierarchical menu tree structure for a specific role.
 */
@UseCase
@RequiredArgsConstructor
public class GetMenuTreeByRoleUseCase {

    private final MenuRepository menuRepository;

    /**
     * Retrieves the menu tree structure for a given role.
     *
     * @param roleId the role ID
     * @return list of menu responses with hierarchical children
     */
    @Transactional(readOnly = true)
    public List<MenuResponse> execute(Long roleId) {
        List<Menu> menus = menuRepository.findAllByRoleId(new RoleId(roleId));

        // Convert to responses
        List<MenuResponse> menuResponses = menus.stream()
                .map(MenuAssembler::toResponse)
                .toList();

        // Build tree structure
        return buildMenuTree(menuResponses);
    }

    /**
     * Builds a hierarchical tree structure from a flat list of menu responses.
     *
     * @param menus flat list of menu responses
     * @return list of root menu responses with nested children
     */
    private List<MenuResponse> buildMenuTree(List<MenuResponse> menus) {
        // Create a map for quick lookup
        Map<Long, MenuResponse> menuMap = menus.stream()
                .collect(Collectors.toMap(MenuResponse::id, menu -> {
                    // Create a new MenuResponse with empty children list
                    return new MenuResponse(
                            menu.id(),
                            menu.name(),
                            menu.code(),
                            menu.path(),
                            menu.icon(),
                            menu.parentId(),
                            menu.sortOrder(),
                            new ArrayList<>()
                    );
                }));

        List<MenuResponse> rootMenus = new ArrayList<>();

        for (MenuResponse menu : menus) {
            if (menu.parentId() == null) {
                // This is a root menu
                rootMenus.add(menuMap.get(menu.id()));
            } else {
                // This is a child menu, add to parent's children
                MenuResponse parent = menuMap.get(menu.parentId());
                if (parent != null) {
                    ((List<MenuResponse>) parent.children()).add(menuMap.get(menu.id()));
                }
            }
        }

        // Sort children by sortOrder
        for (MenuResponse root : rootMenus) {
            sortMenuTree(root);
        }

        // Sort root menus by sortOrder
        rootMenus.sort((m1, m2) -> m1.sortOrder().compareTo(m2.sortOrder()));

        return rootMenus;
    }

    /**
     * Recursively sorts the menu tree by sortOrder.
     */
    private void sortMenuTree(MenuResponse menu) {
        List<MenuResponse> children = (List<MenuResponse>) menu.children();
        if (children != null && !children.isEmpty()) {
            children.sort((m1, m2) -> m1.sortOrder().compareTo(m2.sortOrder()));
            for (MenuResponse child : children) {
                sortMenuTree(child);
            }
        }
    }

}
