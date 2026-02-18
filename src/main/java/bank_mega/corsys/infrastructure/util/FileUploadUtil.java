package bank_mega.corsys.infrastructure.util;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.UUID;

public class FileUploadUtil {

    /**
     * Generates a unique filename using UUID
     *
     * @param originalFileName the original filename to extract extension from
     * @return a unique filename with the same extension
     */
    public static String generateUniqueFilename(String originalFileName) {
        String fileExtension = getFileExtension(originalFileName);
        return UUID.randomUUID() + "." + fileExtension;
    }

    /**
     * Generates a file path based on current date and unique filename
     *
     * @param uniqueFileName the unique filename to use in the path
     * @return a file path in the format documents/year/month/uniqueFileName
     */
    public static String generateFilePath(String uniqueFileName) {
        return "documents/" + LocalDateTime.now().getYear() + "/" +
               LocalDateTime.now().getMonthValue() + "/" + uniqueFileName;
    }

    /**
     * Extracts file extension from filename
     *
     * @param fileName the filename to extract extension from
     * @return the file extension or "bin" if no extension is found
     */
    public static String getFileExtension(String fileName) {
        if (fileName == null || fileName.lastIndexOf(".") == -1) {
            return "bin"; // Default extension if none found
        }
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    /**
     * Validates that the file is not empty
     *
     * @param file the multipart file to validate
     * @throws DomainRuleViolationException if the file is empty
     */
    public static void validateFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new DomainRuleViolationException("File cannot be empty");
        }
    }
}