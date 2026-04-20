package bank_mega.corsys.application.emailtemplate.dto;

import lombok.Builder;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Builder
public record EmailTemplateResponse(
        UUID id,
        String variant,
        String subject,
        String body,
        Boolean isActive,
        Boolean isSendToAnchor,
        Boolean isSendToSupplier,
        Boolean isSendToAdminBank,
        Boolean ccIsSendToRm,
        Boolean ccIsSendToChecker,
        Boolean ccIsSendToSigner,
        Boolean ccIsSendToSpecifiedMail,
        List<VariableResponse> variables,
        List<SpecificEmailResponse> specificEmails,
        Instant createdAt,
        UUID createdBy,
        Instant updatedAt,
        UUID updatedBy,
        Instant deletedAt,
        UUID deletedBy
) {
    @Builder
    public record VariableResponse(
            UUID id,
            String flag
    ) {}

    @Builder
    public record SpecificEmailResponse(
            UUID id,
            UUID userId,
            String email
    ) {}
}
