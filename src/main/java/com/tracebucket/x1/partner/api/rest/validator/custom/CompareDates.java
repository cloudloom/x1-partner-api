package com.tracebucket.x1.partner.api.rest.validator.custom;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Created by sadath on 16-Oct-2015.
 */
@Target({TYPE, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = CompareDatesValidator.class)
@Documented
public @interface CompareDates {

    String message() default "{com.tracebucket.x1.partner.api.rest.validator.custom.CompareDates.message}";

    String invalidTypes() default "{com.tracebucket.x1.partner.api.rest.validator.custom.CompareDates.invalidTypes}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String before();

    String after();
}