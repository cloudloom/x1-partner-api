package com.tracebucket.x1.partner.api.rest.validator.custom;

import org.apache.commons.beanutils.BeanUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Date;

/**
 * Created by sadath on 16-Oct-2015.
 */
public class CompareDatesValidator implements ConstraintValidator<CompareDates, Object> {

    private String before;

    private String after;

    @Override
    public void initialize(CompareDates constraintAnnotation) {
        this.before = constraintAnnotation.before();
        this.after = constraintAnnotation.after();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        boolean status = false;
        try {
            final Object beforeObj = BeanUtils.getProperty(value, this.before);
            final Object afterObj = BeanUtils.getProperty(value, this.after);

            if(!(beforeObj instanceof Date) || !(afterObj instanceof Date)) {
                status = false;
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("invalidTypes")
                        .addNode(before)
                        .addConstraintViolation();
            } else if((beforeObj instanceof Date) && (afterObj instanceof Date)) {
                Date startDate = (Date)beforeObj;
                Date endDate = (Date)afterObj;
                if(startDate.after(endDate)) {
                    status = false;
                    context.disableDefaultConstraintViolation();
                    context.buildConstraintViolationWithTemplate("message")
                            .addNode(before)
                            .addConstraintViolation();
                } else {
                    status = true;
                }
            }
        }
        catch (final Exception ignore) {
        }
        return status;
    }
}
