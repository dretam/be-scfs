package bank_mega.corsys.application.parameter.statuskependudukanpenerima.usecase;

import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.application.parameter.statuskependudukanpenerima.dto.ParameterStatusKependudukanPenerimaResponse;
import bank_mega.corsys.domain.exception.ParameterStatusKependudukanPenerimaNotFoundException;
import bank_mega.corsys.domain.model.parameter.statuskependudukanpenerima.ParameterStatusKependudukanPenerima;
import bank_mega.corsys.domain.model.parameter.statuskependudukanpenerima.ParameterStatusKependudukanPenerimaId;
import bank_mega.corsys.domain.repository.ParameterStatusKependudukanPenerimaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@UseCase
@RequiredArgsConstructor
public class PageParameterStatusKependudukanPenerimaUseCase {

    private final ParameterStatusKependudukanPenerimaRepository parameterStatusKependudukanPenerimaRepository;

    @Transactional(readOnly = true)
    public Page<ParameterStatusKependudukanPenerimaResponse> execute(int page, int perPage, String sort, String filter) {
        Page<ParameterStatusKependudukanPenerima> pageable = parameterStatusKependudukanPenerimaRepository.findAllPageable(page, perPage, sort, filter);

        List<ParameterStatusKependudukanPenerimaResponse> content = pageable.stream()
                .map(p -> ParameterStatusKependudukanPenerimaResponse.builder()
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
