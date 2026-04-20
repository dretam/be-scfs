package bank_mega.corsys.application.assembler;

import bank_mega.corsys.application.emailtemplate.dto.EmailTemplateResponse;
import bank_mega.corsys.domain.model.emailtemplate.EmailTemplate;
import bank_mega.corsys.domain.model.emailtemplate.EmailTemplateSpecificEmail;
import bank_mega.corsys.domain.model.emailtemplate.EmailTemplateVariable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EmailTemplateAssembler {

    public static EmailTemplateResponse toResponse(EmailTemplate template) {
        if (template == null) return null;

        return EmailTemplateResponse.builder()
                .id(template.getId().value())
                .variant(template.getVariant().name())
                .subject(template.getSubject().value())
                .body(template.getBody().value())
                .isActive(template.getIsActive().value())
                .isSendToAnchor(template.getIsSendToAnchor().value())
                .isSendToSupplier(template.getIsSendToSupplier().value())
                .isSendToAdminBank(template.getIsSendToAdminBank().value())
                .ccIsSendToRm(template.getCcIsSendToRm().value())
                .ccIsSendToChecker(template.getCcIsSendToChecker().value())
                .ccIsSendToSigner(template.getCcIsSendToSigner().value())
                .ccIsSendToSpecifiedMail(template.getCcIsSendToSpecifiedMail().value())
                .variables(buildVariableResponses(template.getVariables()))
                .specificEmails(buildSpecificEmailResponses(template.getSpecificEmails()))
                .createdAt(template.getAudit().createdAt())
                .createdBy(template.getAudit().createdBy())
                .updatedAt(template.getAudit().updatedAt())
                .updatedBy(template.getAudit().updatedBy())
                .deletedAt(template.getAudit().deletedAt())
                .deletedBy(template.getAudit().deletedBy())
                .build();
    }

    private static List<EmailTemplateResponse.VariableResponse> buildVariableResponses(Set<EmailTemplateVariable> variables) {
        if (variables == null || variables.isEmpty()) return Collections.emptyList();
        return variables.stream()
                .map(v -> EmailTemplateResponse.VariableResponse.builder()
                        .id(v.getId().value())
                        .flag(v.getFlag().value())
                        .build())
                .collect(Collectors.toList());
    }

    private static List<EmailTemplateResponse.SpecificEmailResponse> buildSpecificEmailResponses(Set<EmailTemplateSpecificEmail> emails) {
        if (emails == null || emails.isEmpty()) return Collections.emptyList();
        return emails.stream()
                .map(e -> EmailTemplateResponse.SpecificEmailResponse.builder()
                        .id(e.getId().value())
                        .userId(e.getUserId().value())
                        .email(e.getEmailAddress().value())
                        .build())
                .collect(Collectors.toList());
    }
}
