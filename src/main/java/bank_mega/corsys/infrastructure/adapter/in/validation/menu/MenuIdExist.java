package bank_mega.corsys.infrastructure.adapter.in.validation.menu;

import bank_mega.corsys.infrastructure.adapter.in.validation.menu.impl.MenuIdExistImpl;
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
@Constraint(validatedBy = MenuIdExistImpl.class)
public @interface MenuIdExist {

    String message() default "Menu data doesn't exist";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
