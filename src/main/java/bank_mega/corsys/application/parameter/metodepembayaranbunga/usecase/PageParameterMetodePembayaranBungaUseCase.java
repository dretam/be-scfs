package bank_mega.corsys.application.parameter.metodepembayaranbunga.usecase;

import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.application.parameter.metodepembayaranbunga.dto.ParameterMetodePembayaranBungaResponse;
import bank_mega.corsys.domain.exception.ParameterMetodePembayaranBungaNotFoundException;
import bank_mega.corsys.domain.model.parameter.metodepembayaranbunga.ParameterMetodePembayaranBunga;
import bank_mega.corsys.domain.model.parameter.metodepembayaranbunga.ParameterMetodePembayaranBungaId;
import bank_mega.corsys.domain.repository.ParameterMetodePembayaranBungaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@UseCase
@RequiredArgsConstructor
public class PageParameterMetodePembayaranBungaUseCase {

    private final ParameterMetodePembayaranBungaRepository parameterMetodePembayaranBungaRepository;

    @Transactional(readOnly = true)
    public Page<ParameterMetodePembayaranBungaResponse> execute(int page, int perPage, String sort, String filter) {
        Page<ParameterMetodePembayaranBunga> pageable = parameterMetodePembayaranBungaRepository.findAllPageable(page, perPage, sort, filter);

        List<ParameterMetodePembayaranBungaResponse> content = pageable.stream()
                .map(p -> ParameterMetodePembayaranBungaResponse.builder()
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
