package bank_mega.corsys.infrastructure.adapter.in.validation.permission;

import bank_mega.corsys.infrastructure.adapter.in.validation.permission.impl.PermissionIdExistImpl;
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
@Constraint(validatedBy = PermissionIdExistImpl.class)
public @interface PermissionIdExist {

    String message() default "Permission data doesn't exist";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
