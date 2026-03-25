package bank_mega.corsys.domain.model.parameter.approverbiayamaterai;

import bank_mega.corsys.domain.model.common.AuditTrail;

public class ParameterApproverBiayaMaterai {

    private final ParameterApproverBiayaMateraiId id;
    private ParameterApproverBiayaMateraiCode code;
    private ParameterApproverBiayaMateraiValue value;
    private AuditTrail audit;

    public ParameterApproverBiayaMaterai(
            ParameterApproverBiayaMateraiId id,
            ParameterApproverBiayaMateraiCode code,
            ParameterApproverBiayaMateraiValue value,
            AuditTrail audit
    ) {
        this.id = id;
        this.code = code;
        this.value = value;
        this.audit = audit;
    }

    public ParameterApproverBiayaMateraiId getId() {
        return id;
    }

    public ParameterApproverBiayaMateraiCode getCode() {
        return code;
    }

    public ParameterApproverBiayaMateraiValue getValue() {
        return value;
    }

    public AuditTrail getAudit() {
        return audit;
    }

    public void changeCode(ParameterApproverBiayaMateraiCode code) {
        if (code != null) {
            this.code = code;
        }
    }

    public void changeValue(ParameterApproverBiayaMateraiValue value) {
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
