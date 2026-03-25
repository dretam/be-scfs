package bank_mega.corsys.domain.model.parameter.jenistransfer;

import bank_mega.corsys.domain.model.common.AuditTrail;

public class ParameterJenisTransfer {

    private final ParameterJenisTransferId id;
    private ParameterJenisTransferCode code;
    private ParameterJenisTransferValue value;
    private AuditTrail audit;

    public ParameterJenisTransfer(
            ParameterJenisTransferId id,
            ParameterJenisTransferCode code,
            ParameterJenisTransferValue value,
            AuditTrail audit
    ) {
        this.id = id;
        this.code = code;
        this.value = value;
        this.audit = audit;
    }

    public ParameterJenisTransferId getId() {
        return id;
    }

    public ParameterJenisTransferCode getCode() {
        return code;
    }

    public ParameterJenisTransferValue getValue() {
        return value;
    }

    public AuditTrail getAudit() {
        return audit;
    }

    public void changeCode(ParameterJenisTransferCode code) {
        if (code != null) {
            this.code = code;
        }
    }

    public void changeValue(ParameterJenisTransferValue value) {
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
