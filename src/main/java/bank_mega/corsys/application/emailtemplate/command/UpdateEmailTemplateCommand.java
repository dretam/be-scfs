package bank_mega.corsys.application.emailtemplate.command;

import bank_mega.corsys.domain.model.emailtemplate.EmailTemplateVariant;
import lombok.Builder;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Builder
public record UpdateEmailTemplateCommand(
        EmailTemplateVariant variant,
        Optional<String> subject,
        Optional<String> body,
        Optional<Boolean> isActive,
        Optional<Boolean> isSendToAnchor,
        Optional<Boolean> isSendToSupplier,
        Optional<Boolean> isSendToAdminBank,
        Optional<Boolean> ccIsSendToRm,
        Optional<Boolean> ccIsSendToChecker,
        Optional<Boolean> ccIsSendToSigner,
        Optional<Boolean> ccIsSendToSpecifiedMail,
        Optional<List<UpdateVariableCommand>> variables,
        Optional<List<UpdateSpecificEmailCommand>> specificEmails
) {
    @Builder
    public record UpdateVariableCommand(
            Optional<UUID> id,
            Optional<String> flag
    ) {}

    @Builder
    public record UpdateSpecificEmailCommand(
            Optional<UUID> id,
            Optional<UUID> userId,
            Optional<String> email
    ) {}
}
