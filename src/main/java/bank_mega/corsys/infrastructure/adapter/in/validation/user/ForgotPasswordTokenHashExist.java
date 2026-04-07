package bank_mega.corsys.infrastructure.adapter.in.validation.user;

import bank_mega.corsys.infrastructure.adapter.in.validation.user.impl.ForgotPasswordTokenHashExistImpl;
import com.nimbusds.jose.Payload;
import jakarta.validation.Constraint;

import java.lang.annotation.*;

@Target({
        ElementType.FIELD,
        ElementType.PARAMETER,
        ElementType.RECORD_COMPONENT
})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {
        ForgotPasswordTokenHashExistImpl.class
})
public @interface ForgotPasswordTokenHashExist {
    String message() default "Forgot password token hash doesn't exist";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}