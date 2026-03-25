package bank_mega.corsys.application.parameter.approverbiayamaterai.command;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SoftDeleteParameterApproverBiayaMateraiCommand(
        @NotNull
        @NotBlank
        String code
) {
}
