package bank_mega.corsys.application.parameter.jenisnasabahpenerima.usecase;

import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.application.parameter.jenisnasabahpenerima.dto.ParameterJenisNasabahPenerimaResponse;
import bank_mega.corsys.domain.exception.ParameterJenisNasabahPenerimaNotFoundException;
import bank_mega.corsys.domain.model.parameter.jenisnasabahpenerima.ParameterJenisNasabahPenerima;
import bank_mega.corsys.domain.model.parameter.jenisnasabahpenerima.ParameterJenisNasabahPenerimaId;
import bank_mega.corsys.domain.repository.ParameterJenisNasabahPenerimaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@UseCase
@RequiredArgsConstructor
public class PageParameterJenisNasabahPenerimaUseCase {

    private final ParameterJenisNasabahPenerimaRepository parameterJenisNasabahPenerimaRepository;

    @Transactional(readOnly = true)
    public Page<ParameterJenisNasabahPenerimaResponse> execute(int page, int perPage, String sort, String filter) {
        Page<ParameterJenisNasabahPenerima> pageable = parameterJenisNasabahPenerimaRepository.findAllPageable(page, perPage, sort, filter);

        List<ParameterJenisNasabahPenerimaResponse> content = pageable.stream()
                .map(p -> ParameterJenisNasabahPenerimaResponse.builder()
                        .code(p.getCode().value())
                        .value(p.getValue().value())
                        .build())
                .toList();

        return new PageImpl<>(
                content,
                PageRequest.of(page - 1, perPage),
                pageable.getTotalElements()
        );
    }

}
