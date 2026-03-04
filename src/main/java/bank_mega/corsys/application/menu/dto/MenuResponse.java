package bank_mega.corsys.application.menu.dto;

import lombok.Builder;

import java.time.Instant;
import java.util.List;

@Builder
public record MenuResponse(
        Long id,
        String name,
        String code,
        String path,
        String icon,
        Long parentId,
        Integer sortOrder,
        List<MenuResponse> children
) {
}
