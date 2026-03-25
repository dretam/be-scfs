package bank_mega.corsys.application.parameter.sumberdana.usecase;

import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.application.parameter.sumberdana.dto.ParameterSumberDanaResponse;
import bank_mega.corsys.domain.exception.ParameterSumberDanaNotFoundException;
import bank_mega.corsys.domain.model.parameter.sumberdana.ParameterSumberDana;
import bank_mega.corsys.domain.model.parameter.sumberdana.ParameterSumberDanaId;
import bank_mega.corsys.domain.repository.ParameterSumberDanaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@UseCase
@RequiredArgsConstructor
public class PageParameterSumberDanaUseCase {

    private final ParameterSumberDanaRepository parameterSumberDanaRepository;

    @Transactional(readOnly = true)
    public Page<ParameterSumberDanaResponse> execute(int page, int perPage, String sort, String filter) {
        Page<ParameterSumberDana> pageable = parameterSumberDanaRepository.findAllPageable(page, perPage, sort, filter);

        List<ParameterSumberDanaResponse> content = pageable.stream()
                .map(p -> ParameterSumberDanaResponse.builder()
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
