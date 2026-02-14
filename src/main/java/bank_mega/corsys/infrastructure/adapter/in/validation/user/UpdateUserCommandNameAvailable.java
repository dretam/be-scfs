package bank_mega.corsys.infrastructure.adapter.in.validation.user;

import bank_mega.corsys.infrastructure.adapter.in.validation.user.impl.UpdateUserCommandNameAvailableImpl;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;


@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UpdateUserCommandNameAvailableImpl.class)
@Documented
public @interface UpdateUserCommandNameAvailable {

    String message() default "User name already exists";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
