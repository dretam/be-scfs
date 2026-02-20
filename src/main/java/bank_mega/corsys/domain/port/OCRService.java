package bank_mega.corsys.domain.port;

import bank_mega.corsys.domain.model.ocr.OCRData;
import bank_mega.corsys.domain.model.user.UserId;

import java.util.List;

public interface OCRService {
    List<OCRData> upload(byte[] fileBytes, String filename, UserId userId);
}
