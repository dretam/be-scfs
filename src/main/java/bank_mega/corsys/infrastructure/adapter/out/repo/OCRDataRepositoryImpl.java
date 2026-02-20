package bank_mega.corsys.infrastructure.adapter.out.repo;

import bank_mega.corsys.domain.model.ocr.OCRData;
import bank_mega.corsys.domain.repository.OCRDataRepository;
import bank_mega.corsys.infrastructure.adapter.out.jpa.entity.OCRDataJpaEntity;
import bank_mega.corsys.infrastructure.adapter.out.jpa.repository.SpringDataOCRDataJpaRepository;
import bank_mega.corsys.infrastructure.adapter.out.mapper.OCRDataMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class OCRDataRepositoryImpl implements OCRDataRepository {
    private final SpringDataOCRDataJpaRepository springDataRepository;

    @Override
    public OCRData save(OCRData ocrData) {
        return OCRDataMapper.toDomain(
                springDataRepository.save(
                        OCRDataMapper.toJpaEntity(ocrData)
                )
        );
    }

    @Override
    public Optional<OCRData> findFirstById(Long id) {
        return springDataRepository
                .findFirstById(id)
                .map(OCRDataMapper::toDomain);
    }

    @Override
    public void delete(OCRData ocrData) {
        springDataRepository.delete(
                OCRDataMapper.toJpaEntity(ocrData)
        );
    }

    @Override
    public List<OCRData> saveAll(List<OCRData> ocrDataList) {

        List<OCRDataJpaEntity> entities = ocrDataList.stream()
                .map(OCRDataMapper::toJpaEntity)
                .toList();

        return springDataRepository
                .saveAll(entities)
                .stream()
                .map(OCRDataMapper::toDomain)
                .toList();
    }

}
