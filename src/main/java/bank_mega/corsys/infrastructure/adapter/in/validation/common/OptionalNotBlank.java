package bank_mega.corsys.infrastructure.adapter.in.validation.common;

import bank_mega.corsys.infrastructure.adapter.in.validation.common.impl.OptionalNotBlankImpl;
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
@Constraint(validatedBy = OptionalNotBlankImpl.class)
public @interface OptionalNotBlank {

    String message() default "Must not be blank";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
