package bank_mega.corsys.domain.model.parameter.automatictransfer;

import bank_mega.corsys.domain.model.common.AuditTrail;

public class ParameterAutomaticTransfer {

    private final ParameterAutomaticTransferId id;
    private ParameterAutomaticTransferCode code;
    private ParameterAutomaticTransferValue value;
    private AuditTrail audit;

    public ParameterAutomaticTransfer(
            ParameterAutomaticTransferId id,
            ParameterAutomaticTransferCode code,
            ParameterAutomaticTransferValue value,
            AuditTrail audit
    ) {
        this.id = id;
        this.code = code;
        this.value = value;
        this.audit = audit;
    }

    public ParameterAutomaticTransferId getId() {
        return id;
    }

    public ParameterAutomaticTransferCode getCode() {
        return code;
    }

    public ParameterAutomaticTransferValue getValue() {
        return value;
    }

    public AuditTrail getAudit() {
        return audit;
    }

    public void changeCode(ParameterAutomaticTransferCode code) {
        if (code != null) {
            this.code = code;
        }
    }

    public void changeValue(ParameterAutomaticTransferValue value) {
        if (value != null) {
            this.value = value;
        }
    }

    public void updateAudit(Long updatedBy) {
        this.audit = this.audit.update(updatedBy);
    }

    public void deleteAudit(Long deletedBy) {
        this.audit = this.audit.delete(deletedBy);
    }

}
