package bank_mega.corsys.domain.model.emailtemplate;

import bank_mega.corsys.domain.model.common.AuditTrail;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
public class EmailTemplate {
    private final EmailTemplateId id;
    private EmailTemplateVariant variant;
    private EmailTemplateSubject subject;
    private EmailTemplateBody body;
    private EmailTemplateIsActive isActive;
    private EmailTemplateIsSendToAnchor isSendToAnchor;
    private EmailTemplateIsSendToSupplier isSendToSupplier;
    private EmailTemplateIsSendToAdminBank isSendToAdminBank;
    private EmailTemplateCcIsSendToRm ccIsSendToRm;
    private EmailTemplateCcIsSendToChecker ccIsSendToChecker;
    private EmailTemplateCcIsSendToSigner ccIsSendToSigner;
    private EmailTemplateCcIsSendToSpecifiedMail ccIsSendToSpecifiedMail;
    private Set<EmailTemplateVariable> variables;
    private Set<EmailTemplateSpecificEmail> specificEmails;
    private AuditTrail audit;

    public EmailTemplate(
            EmailTemplateId id,
            EmailTemplateVariant variant,
            EmailTemplateSubject subject,
            EmailTemplateBody body,
            EmailTemplateIsActive isActive,
            EmailTemplateIsSendToAnchor isSendToAnchor,
            EmailTemplateIsSendToSupplier isSendToSupplier,
            EmailTemplateIsSendToAdminBank isSendToAdminBank,
            EmailTemplateCcIsSendToRm ccIsSendToRm,
            EmailTemplateCcIsSendToChecker ccIsSendToChecker,
            EmailTemplateCcIsSendToSigner ccIsSendToSigner,
            EmailTemplateCcIsSendToSpecifiedMail ccIsSendToSpecifiedMail,
            Set<EmailTemplateVariable> variables,
            Set<EmailTemplateSpecificEmail> specificEmails,
            AuditTrail audit
    ) {
        this.id = id;
        this.variant = variant;
        this.subject = subject;
        this.body = body;
        this.isActive = isActive;
        this.isSendToAnchor = isSendToAnchor;
        this.isSendToSupplier = isSendToSupplier;
        this.isSendToAdminBank = isSendToAdminBank;
        this.ccIsSendToRm = ccIsSendToRm;
        this.ccIsSendToChecker = ccIsSendToChecker;
        this.ccIsSendToSigner = ccIsSendToSigner;
        this.ccIsSendToSpecifiedMail = ccIsSendToSpecifiedMail;
        this.variables = variables != null ? variables : new HashSet<>();
        this.specificEmails = specificEmails != null ? specificEmails : new HashSet<>();
        this.audit = audit;
    }

    public void updateDetails(
            EmailTemplateVariant variant,
            EmailTemplateSubject subject,
            EmailTemplateBody body,
            EmailTemplateIsActive isActive,
            EmailTemplateIsSendToAnchor isSendToAnchor,
            EmailTemplateIsSendToSupplier isSendToSupplier,
            EmailTemplateIsSendToAdminBank isSendToAdminBank,
            EmailTemplateCcIsSendToRm ccIsSendToRm,
            EmailTemplateCcIsSendToChecker ccIsSendToChecker,
            EmailTemplateCcIsSendToSigner ccIsSendToSigner,
            EmailTemplateCcIsSendToSpecifiedMail ccIsSendToSpecifiedMail
    ) {
        if (variant != null) this.variant = variant;
        if (subject != null) this.subject = subject;
        if (body != null) this.body = body;
        if (isActive != null) this.isActive = isActive;
        if (isSendToAnchor != null) this.isSendToAnchor = isSendToAnchor;
        if (isSendToSupplier != null) this.isSendToSupplier = isSendToSupplier;
        if (isSendToAdminBank != null) this.isSendToAdminBank = isSendToAdminBank;
        if (ccIsSendToRm != null) this.ccIsSendToRm = ccIsSendToRm;
        if (ccIsSendToChecker != null) this.ccIsSendToChecker = ccIsSendToChecker;
        if (ccIsSendToSigner != null) this.ccIsSendToSigner = ccIsSendToSigner;
        if (ccIsSendToSpecifiedMail != null) this.ccIsSendToSpecifiedMail = ccIsSendToSpecifiedMail;
    }

    public void updateAudit(UUID updatedBy) {
        this.audit = this.audit.update(updatedBy);
        if (this.variables != null) {
            this.variables.forEach(var -> var.updateAudit(updatedBy));
        }
        if (this.specificEmails != null) {
            this.specificEmails.forEach(email -> email.updateAudit(updatedBy));
        }
    }

    public void deleteAudit(UUID deletedBy) {
        this.audit = this.audit.delete(deletedBy);
        if (this.variables != null) {
            this.variables.forEach(var -> var.deleteAudit(deletedBy));
        }
        if (this.specificEmails != null) {
            this.specificEmails.forEach(email -> email.deleteAudit(deletedBy));
        }
    }
}
