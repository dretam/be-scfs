package bank_mega.corsys.infrastructure.adapter.in.validation.menu;

import bank_mega.corsys.infrastructure.adapter.in.validation.menu.impl.MenuParentIdExistImpl;
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
@Constraint(validatedBy = MenuParentIdExistImpl.class)
public @interface MenuParentIdExist {

    String message() default "Parent menu doesn't exist";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
