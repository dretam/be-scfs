package bank_mega.corsys.infrastructure.adapter.in.validation.permission;

import bank_mega.corsys.infrastructure.adapter.in.validation.permission.impl.PermissionCodeAvailableImpl;
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
@Constraint(validatedBy = PermissionCodeAvailableImpl.class)
public @interface PermissionCodeAvailable {

    String message() default "Permission code already exists";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
