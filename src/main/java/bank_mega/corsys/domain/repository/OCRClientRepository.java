package bank_mega.corsys.domain.repository;

import bank_mega.corsys.domain.model.ocr.OCRData;

import java.util.List;

public interface OCRClientRepository {
    List<OCRData> upload(byte[] fileBytes, String filename);
}