package bank_mega.corsys.application.emailtemplate.usecase;

import bank_mega.corsys.application.assembler.EmailTemplateAssembler;
import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.application.emailtemplate.command.UpdateEmailTemplateCommand;
import bank_mega.corsys.application.emailtemplate.dto.EmailTemplateResponse;
import bank_mega.corsys.domain.exception.EmailTemplateNotFoundException;
import bank_mega.corsys.domain.model.common.AuditTrail;
import bank_mega.corsys.domain.model.emailtemplate.*;
import bank_mega.corsys.domain.model.user.User;
import bank_mega.corsys.domain.repository.EmailTemplateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@UseCase
@RequiredArgsConstructor
public class UpdateEmailTemplateUseCase {

    private final EmailTemplateRepository emailTemplateRepository;

    @Transactional
    public EmailTemplateResponse execute(UpdateEmailTemplateCommand command, User authPrincipal) {
        EmailTemplate template = emailTemplateRepository.findByVariant(command.variant())
                .orElseThrow(() -> new EmailTemplateNotFoundException(command.variant()));

        template.updateDetails(
                null, // variant stays same
                command.subject().map(EmailTemplateSubject::new).orElse(null),
                command.body().map(EmailTemplateBody::new).orElse(null),
                command.isActive().map(EmailTemplateIsActive::new).orElse(null),
                command.isSendToAnchor().map(EmailTemplateIsSendToAnchor::new).orElse(null),
                command.isSendToSupplier().map(EmailTemplateIsSendToSupplier::new).orElse(null),
                command.isSendToAdminBank().map(EmailTemplateIsSendToAdminBank::new).orElse(null),
                command.ccIsSendToRm().map(EmailTemplateCcIsSendToRm::new).orElse(null),
                command.ccIsSendToChecker().map(EmailTemplateCcIsSendToChecker::new).orElse(null),
                command.ccIsSendToSigner().map(EmailTemplateCcIsSendToSigner::new).orElse(null),
                command.ccIsSendToSpecifiedMail().map(EmailTemplateCcIsSendToSpecifiedMail::new).orElse(null)
        );

        command.variables().ifPresent(vCommands -> {
            Set<EmailTemplateVariable> updatedVars = vCommands.stream()
                    .map(vCmd -> {
                        if (vCmd.id().isPresent()) {
                            EmailTemplateVariable existing = template.getVariables().stream()
                                    .filter(v -> v.getId().value().equals(vCmd.id().get()))
                                    .findFirst()
                                    .orElseThrow(() -> new IllegalArgumentException("Variable not found: " + vCmd.id().get()));
                            vCmd.flag().ifPresent(flag -> existing.updateFlag(new EmailTemplateVariableFlag(flag)));
                            existing.updateAudit(authPrincipal.getId().value());
                            return existing;
                        } else {
                            return new EmailTemplateVariable(
                                    null,
                                    new EmailTemplateVariableFlag(vCmd.flag().orElseThrow()),
                                    AuditTrail.create(authPrincipal.getId().value())
                            );
                        }
                    })
                    .collect(Collectors.toSet());
            template.setVariables(updatedVars);
        });

        command.specificEmails().ifPresent(eCommands -> {
            Set<EmailTemplateSpecificEmail> updatedEmails = eCommands.stream()
                    .map(eCmd -> {
                        if (eCmd.id().isPresent()) {
                            EmailTemplateSpecificEmail existing = template.getSpecificEmails().stream()
                                    .filter(e -> e.getId().value().equals(eCmd.id().get()))
                                    .findFirst()
                                    .orElseThrow(() -> new IllegalArgumentException("Specific email not found: " + eCmd.id().get()));
                            existing.updateDetails(
                                    eCmd.userId().map(EmailTemplateSpecificEmailUserId::new).orElse(null),
                                    eCmd.email().map(EmailTemplateSpecificEmailAddress::new).orElse(null)
                            );
                            existing.updateAudit(authPrincipal.getId().value());
                            return existing;
                        } else {
                            return new EmailTemplateSpecificEmail(
                                    null,
                                    new EmailTemplateSpecificEmailUserId(eCmd.userId().orElse(null)),
                                    new EmailTemplateSpecificEmailAddress(eCmd.email().orElse(null)),
                                    AuditTrail.create(authPrincipal.getId().value())
                            );
                        }
                    })
                    .collect(Collectors.toSet());
            template.setSpecificEmails(updatedEmails);
        });

        template.updateAudit(authPrincipal.getId().value());
        EmailTemplate saved = emailTemplateRepository.save(template);

        return EmailTemplateAssembler.toResponse(saved);
    }
}
