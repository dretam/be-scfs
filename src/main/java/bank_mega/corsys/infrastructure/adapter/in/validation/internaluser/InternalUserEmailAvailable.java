package bank_mega.corsys.infrastructure.adapter.in.validation.internaluser;

import bank_mega.corsys.infrastructure.adapter.in.validation.internaluser.impl.InternalUserEmailAvailableImpl;
import bank_mega.corsys.infrastructure.adapter.in.validation.internaluser.impl.InternalUserEmailOptionalAvailableImpl;
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
        InternalUserEmailAvailableImpl.class,
        InternalUserEmailOptionalAvailableImpl.class
})
public @interface InternalUserEmailAvailable {

    String message() default "InternalUser email already exists";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
