package bank_mega.corsys.application.menu.command;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.util.UUID;

@Builder
public record CreateMenuCommand(
        @NotNull
        @NotBlank
        @Size(min = 3, max = 255, message = "Menu name must be between 3 and 255 characters")
        String name,

        @NotNull
        @NotBlank
        @Size(min = 3, max = 100, message = "Menu code must be between 3 and 100 characters")
        String code,

        @Size(max = 500, message = "Path must not exceed 500 characters")
        String path,

        @Size(max = 100, message = "Icon must not exceed 100 characters")
        String icon,

        UUID parentId,

        @NotNull
        Integer sortOrder
) {
}
