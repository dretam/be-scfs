package bank_mega.corsys.infrastructure.adapter.in.validation.menu.impl;

import bank_mega.corsys.domain.model.menu.Menu;
import bank_mega.corsys.domain.model.menu.MenuCode;
import bank_mega.corsys.domain.repository.MenuRepository;
import bank_mega.corsys.infrastructure.adapter.in.validation.menu.MenuCodeAvailable;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

@RequiredArgsConstructor
public class MenuCodeAvailableImpl implements ConstraintValidator<MenuCodeAvailable, String> {

    private final MenuRepository menuRepository;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (Objects.nonNull(value) && !value.isBlank()) {
            Menu existing = menuRepository.findFirstByCode(new MenuCode(value)).orElse(null);
            return existing == null;
        }
        return true;
    }

}
