package bank_mega.corsys.infrastructure.adapter.in.validation.roleChildren;

import bank_mega.corsys.infrastructure.adapter.in.validation.roleChildren.impl.RoleChildrenIdExistImpl;
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
        RoleChildrenIdExistImpl.class
})
public @interface RoleChildrenIdExist {

    String message() default "Role children data doesn't exist";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
