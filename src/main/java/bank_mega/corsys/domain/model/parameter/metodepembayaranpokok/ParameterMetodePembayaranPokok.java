package bank_mega.corsys.domain.model.parameter.metodepembayaranpokok;

import bank_mega.corsys.domain.model.common.AuditTrail;

public class ParameterMetodePembayaranPokok {

    private final ParameterMetodePembayaranPokokId id;
    private ParameterMetodePembayaranPokokCode code;
    private ParameterMetodePembayaranPokokValue value;
    private AuditTrail audit;

    public ParameterMetodePembayaranPokok(
            ParameterMetodePembayaranPokokId id,
            ParameterMetodePembayaranPokokCode code,
            ParameterMetodePembayaranPokokValue value,
            AuditTrail audit
    ) {
        this.id = id;
        this.code = code;
        this.value = value;
        this.audit = audit;
    }

    public ParameterMetodePembayaranPokokId getId() {
        return id;
    }

    public ParameterMetodePembayaranPokokCode getCode() {
        return code;
    }

    public ParameterMetodePembayaranPokokValue getValue() {
        return value;
    }

    public AuditTrail getAudit() {
        return audit;
    }

    public void changeCode(ParameterMetodePembayaranPokokCode code) {
        if (code != null) {
            this.code = code;
        }
    }

    public void changeValue(ParameterMetodePembayaranPokokValue value) {
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
