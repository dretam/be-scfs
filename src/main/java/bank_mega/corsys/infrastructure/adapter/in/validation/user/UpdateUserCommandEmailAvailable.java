package bank_mega.corsys.infrastructure.adapter.in.validation.user;

import bank_mega.corsys.infrastructure.adapter.in.validation.user.impl.UpdateUserCommandEmailAvailableImpl;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;


@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UpdateUserCommandEmailAvailableImpl.class)
@Documented
public @interface UpdateUserCommandEmailAvailable {

    String message() default "User email already exists";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
