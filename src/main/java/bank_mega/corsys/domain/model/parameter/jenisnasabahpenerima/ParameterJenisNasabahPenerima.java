package bank_mega.corsys.domain.model.parameter.jenisnasabahpenerima;

import bank_mega.corsys.domain.model.common.AuditTrail;

public class ParameterJenisNasabahPenerima {

    private final ParameterJenisNasabahPenerimaId id;
    private ParameterJenisNasabahPenerimaCode code;
    private ParameterJenisNasabahPenerimaValue value;
    private AuditTrail audit;

    public ParameterJenisNasabahPenerima(
            ParameterJenisNasabahPenerimaId id,
            ParameterJenisNasabahPenerimaCode code,
            ParameterJenisNasabahPenerimaValue value,
            AuditTrail audit
    ) {
        this.id = id;
        this.code = code;
        this.value = value;
        this.audit = audit;
    }

    public ParameterJenisNasabahPenerimaId getId() {
        return id;
    }

    public ParameterJenisNasabahPenerimaCode getCode() {
        return code;
    }

    public ParameterJenisNasabahPenerimaValue getValue() {
        return value;
    }

    public AuditTrail getAudit() {
        return audit;
    }

    public void changeCode(ParameterJenisNasabahPenerimaCode code) {
        if (code != null) {
            this.code = code;
        }
    }

    public void changeValue(ParameterJenisNasabahPenerimaValue value) {
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
