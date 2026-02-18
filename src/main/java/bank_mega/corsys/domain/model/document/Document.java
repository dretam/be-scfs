package bank_mega.corsys.domain.model.document;

import bank_mega.corsys.domain.model.common.AuditTrail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Document {

    private Long id;
    private String filename;
    private String originalName;
    private String filePath;
    private Long fileSize;
    private String mimeType;
    private Long uploadedBy;
    private Long userId;
    private AuditTrail audit;

    public void updateAudit(Long updatedBy) {
        this.audit = this.audit.update(updatedBy);
    }

    public void deleteAudit(Long deletedBy) {
        this.audit = this.audit.delete(deletedBy);
    }
}