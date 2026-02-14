package bank_mega.corsys.infrastructure.adapter.in.validation.role;

import bank_mega.corsys.infrastructure.adapter.in.validation.role.impl.RoleNameAvailableImpl;
import bank_mega.corsys.infrastructure.adapter.in.validation.role.impl.RoleNameOptionalAvailableImpl;
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
        RoleNameAvailableImpl.class,
        RoleNameOptionalAvailableImpl.class
})
public @interface RoleNameAvailable {

    String message() default "Role name already exists";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
