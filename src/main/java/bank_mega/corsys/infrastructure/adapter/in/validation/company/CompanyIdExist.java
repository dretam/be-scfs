package bank_mega.corsys.infrastructure.adapter.in.validation.company;

import bank_mega.corsys.infrastructure.adapter.in.validation.company.impl.CompanyIdExistImpl;
import bank_mega.corsys.infrastructure.adapter.in.validation.role.impl.RoleIdExistImpl;
import bank_mega.corsys.infrastructure.adapter.in.validation.role.impl.RoleIdOptionalExistImpl;
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
        CompanyIdExistImpl.class
})
public @interface CompanyIdExist {

    String message() default "Company data doesn't exist";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
