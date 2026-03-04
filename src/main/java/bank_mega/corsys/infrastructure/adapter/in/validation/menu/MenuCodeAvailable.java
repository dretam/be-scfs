package bank_mega.corsys.infrastructure.adapter.in.validation.menu;

import bank_mega.corsys.infrastructure.adapter.in.validation.menu.impl.MenuCodeAvailableImpl;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({
        ElementType.FIELD,
        ElementType.PARAMETER,
        ElementType.RECORD_COMPONENT
})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = MenuCodeAvailableImpl.class)
public @interface MenuCodeAvailable {

    String message() default "Menu code already exists";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
