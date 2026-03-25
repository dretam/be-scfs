package bank_mega.corsys.application.parameter.jenisperpanjangan.usecase;

import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.application.parameter.jenisperpanjangan.dto.ParameterJenisPerpanjanganResponse;
import bank_mega.corsys.domain.exception.ParameterJenisPerpanjanganNotFoundException;
import bank_mega.corsys.domain.model.parameter.jenisperpanjangan.ParameterJenisPerpanjangan;
import bank_mega.corsys.domain.model.parameter.jenisperpanjangan.ParameterJenisPerpanjanganId;
import bank_mega.corsys.domain.repository.ParameterJenisPerpanjanganRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@UseCase
@RequiredArgsConstructor
public class PageParameterJenisPerpanjanganUseCase {

    private final ParameterJenisPerpanjanganRepository parameterJenisPerpanjanganRepository;

    @Transactional(readOnly = true)
    public Page<ParameterJenisPerpanjanganResponse> execute(int page, int perPage, String sort, String filter) {
        Page<ParameterJenisPerpanjangan> pageable = parameterJenisPerpanjanganRepository.findAllPageable(page, perPage, sort, filter);

        List<ParameterJenisPerpanjanganResponse> content = pageable.stream()
                .map(p -> ParameterJenisPerpanjanganResponse.builder()
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
