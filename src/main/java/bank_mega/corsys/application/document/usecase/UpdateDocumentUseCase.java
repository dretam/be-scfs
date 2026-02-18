package bank_mega.corsys.application.document.usecase;

import bank_mega.corsys.application.assembler.DocumentAssembler;
import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.application.document.command.UpdateDocumentCommand;
import bank_mega.corsys.application.document.command.UpdateDocumentMultipartCommand;
import bank_mega.corsys.application.document.dto.DocumentResponse;
import bank_mega.corsys.domain.exception.DocumentAlreadyExistsException;
import bank_mega.corsys.domain.exception.DocumentNotFoundException;
import bank_mega.corsys.domain.exception.DomainRuleViolationException;
import bank_mega.corsys.domain.model.common.Id;
import bank_mega.corsys.domain.model.document.Document;
import bank_mega.corsys.domain.repository.DocumentRepository;
import bank_mega.corsys.domain.repository.StorageRepository;
import bank_mega.corsys.infrastructure.config.S3ConfigProperties;
import bank_mega.corsys.infrastructure.util.FileUploadUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@UseCase
@RequiredArgsConstructor
public class UpdateDocumentUseCase {

    private final DocumentRepository documentRepository;
    private final StorageRepository storageRepository;
    private final S3ConfigProperties s3ConfigProperties;

    @Transactional
    public DocumentResponse execute(UpdateDocumentMultipartCommand command, bank_mega.corsys.domain.model.user.User authPrincipal) throws IOException {
        MultipartFile file = command.file();
        Long documentId = command.id();
        Long uploadedBy = authPrincipal.getId().value();

        // Find existing document
        Document existingDocument = documentRepository.findFirstByIdAndAuditDeletedAtIsNull(new Id(documentId))
                .orElseThrow(() -> new DocumentNotFoundException(new Id(documentId)));

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

        // Check if filename is being changed and if the new filename already exists
        if (!existingDocument.getOriginalName().equals(originalFileName)) {
            documentRepository.findFirstByFilenameAndAuditDeletedAtIsNull(originalFileName)
                    .ifPresent(existing -> {
                        if (!existing.getId().equals(existingDocument.getId())) {
                            throw new DocumentAlreadyExistsException(originalFileName);
                        }
                    });
        }

        // Update document fields with file metadata
        existingDocument.setFilename(uniqueFileName);
        existingDocument.setOriginalName(originalFileName);
        existingDocument.setFilePath(filePath);
        existingDocument.setFileSize(file.getSize());
        existingDocument.setMimeType(file.getContentType());
        existingDocument.setUploadedBy(uploadedBy);
        existingDocument.updateAudit(authPrincipal.getId().value());

        // Save updated document
        Document updatedDocument = documentRepository.save(existingDocument);

        // Convert to response DTO
        return DocumentAssembler.toResponse(updatedDocument);
    }
}