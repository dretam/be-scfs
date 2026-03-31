package bank_mega.corsys.infrastructure.adapter.in.validation.menu.impl;

import bank_mega.corsys.domain.model.menu.Menu;
import bank_mega.corsys.domain.model.menu.MenuId;
import bank_mega.corsys.domain.repository.MenuRepository;
import bank_mega.corsys.infrastructure.adapter.in.validation.menu.MenuIdExist;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

import java.util.Objects;
import java.util.UUID;

@RequiredArgsConstructor
public class MenuIdExistImpl implements ConstraintValidator<MenuIdExist, UUID> {

    private final MenuRepository menuRepository;

    @Override
    public boolean isValid(UUID value, ConstraintValidatorContext context) {
        if (Objects.nonNull(value)) {
            Menu menu = menuRepository.findFirstByIdAndAuditDeletedAtIsNull(
                    new MenuId(value)).orElse(null);
            return menu != null;
        } else {
            return true;
        }
    }

}
