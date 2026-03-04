package bank_mega.corsys.infrastructure.config.security;

import java.lang.annotation.*;

/**
 * Custom annotation for permission-based access control.
 * Usage: @HasPermission("USER_CREATE")
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface HasPermission {

    /**
     * The permission code required to access the resource.
     * Example: "USER_CREATE", "ROLE_READ", etc.
     */
    String value();

}
