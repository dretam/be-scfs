package bank_mega.corsys.infrastructure.adapter.in.validation.user;

import bank_mega.corsys.infrastructure.adapter.in.validation.user.impl.UserNameAvailableImpl;
import bank_mega.corsys.infrastructure.adapter.in.validation.user.impl.UserNameOptionalAvailableImpl;
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
        UserNameAvailableImpl.class,
        UserNameOptionalAvailableImpl.class
})
public @interface UserNameAvailable {

    String message() default "User name already exists";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
