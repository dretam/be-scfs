package bank_mega.corsys.application.document.usecase;

import bank_mega.corsys.application.assembler.DocumentAssembler;
import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.application.document.command.CreateDocumentMultipartMultipleCommand;
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
import java.util.ArrayList;
import java.util.List;

@Slf4j
@UseCase
@RequiredArgsConstructor
public class CreateMultipleDocumentUseCase {

    private final DocumentRepository documentRepository;
    private final StorageRepository storageRepository;
    private final S3ConfigProperties s3ConfigProperties;

    @Transactional
    public List<DocumentResponse> execute(
            CreateDocumentMultipartMultipleCommand command,
            bank_mega.corsys.domain.model.user.User authPrincipal
    ) throws IOException {

        Long uploadedBy = authPrincipal.getId().value();
        List<MultipartFile> files = command.files();

        if (files == null || files.isEmpty()) {
            throw new DomainRuleViolationException("Files must not be empty.");
        }

        if (s3ConfigProperties.getBucketName() == null ||
            s3ConfigProperties.getBucketName().isEmpty()) {
            throw new DomainRuleViolationException(
                    "S3 configuration is required for file uploads."
            );
        }

        List<DocumentResponse> responses = new ArrayList<>();

        for (MultipartFile file : files) {

            // Validate
            FileUploadUtil.validateFile(file);

            String originalFileName = file.getOriginalFilename();
            String uniqueFileName =
                    FileUploadUtil.generateUniqueFilename(originalFileName);
            String filePath =
                    FileUploadUtil.generateFilePath(uniqueFileName);

            try {
                storageRepository.uploadFile(
                        s3ConfigProperties.getBucketName(),
                        filePath,
                        file.getInputStream(),
                        file.getContentType()
                );
            } catch (IOException e) {
                throw new DomainRuleViolationException(
                        "Failed to upload file: " + originalFileName
                );
            }

            // Optional: check duplicate original filename
            documentRepository
                    .findFirstByFilenameAndAuditDeletedAtIsNull(originalFileName)
                    .ifPresent(existing -> {
                        throw new DocumentAlreadyExistsException(originalFileName);
                    });

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

            Document saved = documentRepository.save(newDocument);
            responses.add(DocumentAssembler.toResponse(saved));
        }

        return responses;
    }
}