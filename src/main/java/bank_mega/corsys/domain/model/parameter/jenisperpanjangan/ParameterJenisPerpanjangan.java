package bank_mega.corsys.domain.model.parameter.jenisperpanjangan;

import bank_mega.corsys.domain.model.common.AuditTrail;

public class ParameterJenisPerpanjangan {

    private final ParameterJenisPerpanjanganId id;
    private ParameterJenisPerpanjanganCode code;
    private ParameterJenisPerpanjanganValue value;
    private AuditTrail audit;

    public ParameterJenisPerpanjangan(
            ParameterJenisPerpanjanganId id,
            ParameterJenisPerpanjanganCode code,
            ParameterJenisPerpanjanganValue value,
            AuditTrail audit
    ) {
        this.id = id;
        this.code = code;
        this.value = value;
        this.audit = audit;
    }

    public ParameterJenisPerpanjanganId getId() {
        return id;
    }

    public ParameterJenisPerpanjanganCode getCode() {
        return code;
    }

    public ParameterJenisPerpanjanganValue getValue() {
        return value;
    }

    public AuditTrail getAudit() {
        return audit;
    }

    public void changeCode(ParameterJenisPerpanjanganCode code) {
        if (code != null) {
            this.code = code;
        }
    }

    public void changeValue(ParameterJenisPerpanjanganValue value) {
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
