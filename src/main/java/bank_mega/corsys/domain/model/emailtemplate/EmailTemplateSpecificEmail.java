package bank_mega.corsys.domain.model.emailtemplate;

import bank_mega.corsys.domain.model.common.AuditTrail;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class EmailTemplateSpecificEmail {
    private final EmailTemplateSpecificEmailId id;
    private EmailTemplateSpecificEmailUserId userId;
    private EmailTemplateSpecificEmailAddress emailAddress;
    private AuditTrail audit;

    public EmailTemplateSpecificEmail(EmailTemplateSpecificEmailId id, EmailTemplateSpecificEmailUserId userId, EmailTemplateSpecificEmailAddress emailAddress, AuditTrail audit) {
        this.id = id;
        this.userId = userId;
        this.emailAddress = emailAddress;
        this.audit = audit;
    }

    public void updateDetails(EmailTemplateSpecificEmailUserId userId, EmailTemplateSpecificEmailAddress emailAddress) {
        if (userId != null) this.userId = userId;
        if (emailAddress != null) this.emailAddress = emailAddress;
    }

    public void updateAudit(UUID updatedBy) {
        this.audit = this.audit.update(updatedBy);
    }

    public void deleteAudit(UUID deletedBy) {
        this.audit = this.audit.delete(deletedBy);
    }
}
