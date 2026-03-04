package bank_mega.corsys.application.permission.command;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record CreatePermissionCommand(
        @NotNull
        @NotBlank
        @Size(min = 3, max = 255, message = "Permission name must be between 3 and 255 characters")
        String name,

        @NotNull
        @NotBlank
        @Size(min = 3, max = 100, message = "Permission code must be between 3 and 100 characters")
        String code,

        @Size(max = 1000, message = "Description must not exceed 1000 characters")
        String description
) {
}
