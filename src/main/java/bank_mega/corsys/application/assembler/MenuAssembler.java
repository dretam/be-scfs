package bank_mega.corsys.application.assembler;

import bank_mega.corsys.application.menu.dto.MenuResponse;
import bank_mega.corsys.domain.model.menu.Menu;

public class MenuAssembler {

    public static MenuResponse toResponse(Menu menu) {
        if (menu == null) return null;
        return MenuResponse.builder()
                .id(menu.getId().value())
                .name(menu.getName().value())
                .code(menu.getCode().value())
                .path(menu.getPath())
                .icon(menu.getIcon())
                .parentId(menu.getParentId() != null ? menu.getParentId().value() : null)
                .sortOrder(menu.getSortOrder())
                .build();
    }

}
