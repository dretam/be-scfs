package bank_mega.corsys.application.document.usecase;

import bank_mega.corsys.application.assembler.DocumentAssembler;
import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.application.document.command.CreateDocumentCommand;
import bank_mega.corsys.application.document.command.CreateDocumentMultipartCommand;
import bank_mega.corsys.application.document.dto.DocumentResponse;
import bank_mega.corsys.domain.exception.DocumentAlreadyExistsException;
import bank_mega.corsys.domain.exception.DomainRuleViolationException;
import bank_mega.corsys.domain.model.common.AuditTrail;
import bank_mega.corsys.domain.model.document.Document;
import bank_mega.corsys.domain.repository.DocumentRepository;
import bank_mega.corsys.domain.repository.StorageRepository;
import bank_mega.corsys.infrastructure.config.S3ConfigProperties;
import bank_mega.corsys.infrastructure.util.FileUploadUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@UseCase
@RequiredArgsConstructor
public class CreateDocumentUseCase {

    private final DocumentRepository documentRepository;
    private final StorageRepository storageRepository;
    private final S3ConfigProperties s3ConfigProperties;

    @Transactional
    public DocumentResponse execute(CreateDocumentMultipartCommand command, bank_mega.corsys.domain.model.user.User authPrincipal) throws IOException {
        MultipartFile file = command.file();
        Long uploadedBy = authPrincipal.getId().value();

        // Validate file
        FileUploadUtil.validateFile(file);

        // Generate unique filename and file path
        String originalFileName = file.getOriginalFilename();
        String uniqueFileName = FileUploadUtil.generateUniqueFilename(originalFileName);
        String filePath = FileUploadUtil.generateFilePath(uniqueFileName);

        // Upload to S3 storage
        try {
            if (s3ConfigProperties.getBucketName() == null || s3ConfigProperties.getBucketName().isEmpty()) {
                throw new DomainRuleViolationException("S3 configuration is required for file uploads. Please configure S3 bucket settings.");
            }

            storageRepository.uploadFile(s3ConfigProperties.getBucketName(), filePath, file.getInputStream(), file.getContentType());
        } catch (IOException e) {
            throw new DomainRuleViolationException("Failed to upload file to S3: " + e.getMessage());
        }

        // Check if document with same original filename already exists
        documentRepository.findFirstByFilenameAndAuditDeletedAtIsNull(originalFileName)
                .ifPresent(existing -> {
                    throw new DocumentAlreadyExistsException(originalFileName);
                });

        // Create document entity with audit trail
        Document newDocument = Document.builder()
                .filename(uniqueFileName)
                .originalName(originalFileName)
                .filePath(filePath)
                .fileSize(file.getSize())
                .mimeType(file.getContentType())
                .uploadedBy(uploadedBy)
                .userId(uploadedBy)  // Set userId from the authenticated user
                .audit(AuditTrail.create(uploadedBy))
                .build();

        // Save document to database
        Document savedDocument = documentRepository.save(newDocument);

        // Convert to response DTO
        return DocumentAssembler.toResponse(savedDocument);
    }
}