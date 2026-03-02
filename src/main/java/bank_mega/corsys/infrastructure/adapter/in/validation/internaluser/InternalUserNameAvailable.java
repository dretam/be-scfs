package bank_mega.corsys.infrastructure.adapter.in.validation.internaluser;

import bank_mega.corsys.infrastructure.adapter.in.validation.internaluser.impl.InternalUserNameAvailableImpl;
import bank_mega.corsys.infrastructure.adapter.in.validation.internaluser.impl.InternalUserNameOptionalAvailableImpl;
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
        InternalUserNameAvailableImpl.class,
        InternalUserNameOptionalAvailableImpl.class
})
public @interface InternalUserNameAvailable {

    String message() default "InternalUser userName already exists";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
