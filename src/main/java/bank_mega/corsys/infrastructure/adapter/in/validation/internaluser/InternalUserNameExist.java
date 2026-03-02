package bank_mega.corsys.infrastructure.adapter.in.validation.internaluser;

import bank_mega.corsys.infrastructure.adapter.in.validation.internaluser.impl.InternalUserNameExistImpl;
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
@Constraint(validatedBy = InternalUserNameExistImpl.class)
public @interface InternalUserNameExist {

    String message() default "InternalUser data doesn't exist";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
