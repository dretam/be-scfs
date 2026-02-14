package bank_mega.corsys.infrastructure.adapter.in.validation.user;

import bank_mega.corsys.infrastructure.adapter.in.validation.user.impl.UserEmailAvailableImpl;
import bank_mega.corsys.infrastructure.adapter.in.validation.user.impl.UserEmailOptionalAvailableImpl;
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
        UserEmailAvailableImpl.class,
        UserEmailOptionalAvailableImpl.class
})
public @interface UserEmailAvailable {

    String message() default "User email already exists";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
