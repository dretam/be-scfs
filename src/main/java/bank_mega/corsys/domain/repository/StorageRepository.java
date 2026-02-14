package bank_mega.corsys.domain.repository;

import java.io.InputStream;
import java.util.List;
import software.amazon.awssdk.services.s3.model.S3Object;

public interface StorageRepository {
    void uploadFile(String bucketName, String key, byte[] content, String contentType);
    void uploadFile(String bucketName, String key, InputStream inputStream, String contentType);
    byte[] downloadFile(String bucketName, String key);
    void deleteFile(String bucketName, String key);
    boolean fileExists(String bucketName, String key);
    List<S3Object> listObjects(String bucketName);
}