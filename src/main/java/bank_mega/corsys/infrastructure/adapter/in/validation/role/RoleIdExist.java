package bank_mega.corsys.infrastructure.adapter.in.validation.role;

import bank_mega.corsys.infrastructure.adapter.in.validation.role.impl.RoleIdExistImpl;
import bank_mega.corsys.infrastructure.adapter.in.validation.role.impl.RoleIdOptionalExistImpl;
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
@Constraint(validatedBy = {
        RoleIdExistImpl.class,
        RoleIdOptionalExistImpl.class
})
public @interface RoleIdExist {

    String message() default "Role data doesn't exist";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
