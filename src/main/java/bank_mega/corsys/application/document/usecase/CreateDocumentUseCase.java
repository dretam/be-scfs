package bank_mega.corsys.application.document.usecase;

import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.application.document.command.CreateDocumentMultipartCommand;
import bank_mega.corsys.application.document.dto.OCRResponse;
import bank_mega.corsys.domain.exception.DomainRuleViolationException;
import bank_mega.corsys.domain.model.ocr.OCRData;
import bank_mega.corsys.domain.model.ocr.OCRStatus;
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

    private final OCRService ocrService;

    @Transactional
    public List<OCRResponse> execute(
            CreateDocumentMultipartCommand command,
            User authPrincipal
    ) throws IOException {

        MultipartFile file = command.file();

        FileUploadUtil.validateFile(file);

        String originalFileName = file.getOriginalFilename();

        List<OCRData> ocrResults;

        try {
            ocrResults = ocrService.upload(
                    file.getBytes(),
                    originalFileName,
                    authPrincipal.getId()
            );
        } catch (Exception e) {
            throw new DomainRuleViolationException("OCR processing failed");
        }


        return ocrResults.stream()
                .map(ocrData -> OCRResponse.builder()
                        .status(OCRStatus.PENDING)
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
                        .build())
                .toList();
    }
}
