package bank_mega.corsys.infrastructure.adapter.in.validation.user;

import bank_mega.corsys.infrastructure.adapter.in.validation.user.impl.UserPasswordConfirmationImpl;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({
        ElementType.FIELD,
        ElementType.TYPE,
        ElementType.PARAMETER,
        ElementType.RECORD_COMPONENT
})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {
        UserPasswordConfirmationImpl.class
})
public @interface UserPasswordConfirmation {
    String message() default "Password and password confirmation not match";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
