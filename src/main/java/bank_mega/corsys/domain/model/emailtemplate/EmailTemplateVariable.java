package bank_mega.corsys.domain.model.emailtemplate;

import bank_mega.corsys.domain.model.common.AuditTrail;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class EmailTemplateVariable {
    private final EmailTemplateVariableId id;
    private EmailTemplateVariableFlag flag;
    private AuditTrail audit;

    public EmailTemplateVariable(EmailTemplateVariableId id, EmailTemplateVariableFlag flag, AuditTrail audit) {
        this.id = id;
        this.flag = flag;
        this.audit = audit;
    }

    public void updateFlag(EmailTemplateVariableFlag flag) {
        if (flag != null) this.flag = flag;
    }

    public void updateAudit(UUID updatedBy) {
        this.audit = this.audit.update(updatedBy);
    }

    public void deleteAudit(UUID deletedBy) {
        this.audit = this.audit.delete(deletedBy);
    }
}
