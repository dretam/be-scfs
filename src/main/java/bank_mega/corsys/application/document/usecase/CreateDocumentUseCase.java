package bank_mega.corsys.application.document.usecase;

import bank_mega.corsys.application.assembler.OCRAssembler;
import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.application.document.command.CreateDocumentMultipartCommand;
import bank_mega.corsys.application.document.dto.OCRResponse;
import bank_mega.corsys.domain.exception.DocumentAlreadyExistsException;
import bank_mega.corsys.domain.exception.DomainRuleViolationException;
import bank_mega.corsys.domain.model.common.AuditTrail;
import bank_mega.corsys.domain.model.document.Document;
import bank_mega.corsys.domain.model.ocr.OCRData;
import bank_mega.corsys.domain.model.user.User;
import bank_mega.corsys.domain.port.OCRService;
import bank_mega.corsys.domain.repository.DocumentRepository;
import bank_mega.corsys.domain.port.StorageService;
import bank_mega.corsys.domain.repository.OCRDataRepository;
import bank_mega.corsys.infrastructure.config.S3ConfigProperties;
import bank_mega.corsys.infrastructure.util.FileUploadUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@UseCase
@RequiredArgsConstructor
public class CreateDocumentUseCase {

    private final DocumentRepository documentRepository;
    private final OCRDataRepository ocrDataRepository;
    private final StorageService storageService;
    private final S3ConfigProperties s3ConfigProperties;
    private final OCRService ocrService;

    @Transactional
    public List<OCRResponse> execute(
            CreateDocumentMultipartCommand command,
            User authPrincipal
    ) throws IOException {

        MultipartFile file = command.file();
        Long uploadedBy = authPrincipal.getId().value();

        FileUploadUtil.validateFile(file);

        String originalFileName = file.getOriginalFilename();
        String uniqueFileName = FileUploadUtil.generateUniqueFilename(originalFileName);
        String filePath = FileUploadUtil.generateFilePath(uniqueFileName);
        try {
            if (s3ConfigProperties.getBucketName() == null
                || s3ConfigProperties.getBucketName().isEmpty()) {
                throw new DomainRuleViolationException(
                        "S3 configuration is required for file uploads."
                );
            }

            storageService.uploadFile(
                    s3ConfigProperties.getBucketName(),
                    filePath,
                    file.getInputStream(),
                    file.getContentType()
            );

        } catch (IOException e) {
            throw new DomainRuleViolationException(
                    "Failed to upload file to S3: " + e.getMessage()
            );
        }

        documentRepository.findFirstByFilenameAndAuditDeletedAtIsNull(originalFileName)
                .ifPresent(existing -> {
                    throw new DocumentAlreadyExistsException(originalFileName);
                });

        List<OCRData> ocrResults;

        try {
            ocrResults = ocrService.upload(
                    file.getBytes(),
                    originalFileName,
                    authPrincipal.getId()
            );

            log.info("OCR extracted {} records", ocrResults.size());

        } catch (Exception e) {
            log.error("OCR processing failed", e);
            throw new DomainRuleViolationException("OCR processing failed");
        }


        Document newDocument = Document.builder()
                .filename(uniqueFileName)
                .originalName(originalFileName)
                .filePath(filePath)
                .fileSize(file.getSize())
                .mimeType(file.getContentType())
                .uploadedBy(uploadedBy)
                .userId(uploadedBy)
                .audit(AuditTrail.create(uploadedBy))
                .build();

        Document savedDocument = documentRepository.save(newDocument);

        List<OCRData> ocrDataList = ocrResults.stream()
                .map(ocrData -> OCRData.builder()
                        .documentId(savedDocument.getId())
                        .atasNama(ocrData.getAtasNama())
                        .nominal(ocrData.getNominal())
                        .jangkaWaktu(ocrData.getJangkaWaktu())
                        .periode(ocrData.getPeriode())
                        .rate(ocrData.getRate())
                        .alokasi(ocrData.getAlokasi())
                        .namaRekeningTujuanPencairan(ocrData.getNamaRekeningTujuanPencairan())
                        .nomorRekeningTujuanPencairan(ocrData.getNomorRekeningTujuanPencairan())
                        .nomorRekeningPengirim(ocrData.getNomorRekeningPengirim())
                        .nomorRekeningPlacement(ocrData.getNomorRekeningPlacement())
                        .audit(ocrData.getAudit())
                        .build())
                .toList();

        List<OCRData> savedOcrDataList = ocrDataRepository.saveAll(ocrDataList);

        return savedOcrDataList
                .stream()
                .map(OCRAssembler::toResponse)
                .toList();
    }
}
