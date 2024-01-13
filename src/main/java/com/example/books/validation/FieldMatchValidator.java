package com.example.books.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.beanutils.BeanUtils;

public class FieldMatchValidator implements ConstraintValidator<FieldMatch, Object> {
    private String firstFieldName;
    private String secondFieldName;
    private String message;

    @Override
    public void initialize(FieldMatch constraintAnnotation) {
        firstFieldName = constraintAnnotation.firstString();
        secondFieldName = constraintAnnotation.secondString();
        message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        boolean valid = true;
        try
        {
            final Object firstObject = BeanUtils.getProperty(value, firstFieldName);
            final Object secondObject = BeanUtils.getProperty(value, secondFieldName);

            valid = firstObject == null && secondObject == null ||
                    firstObject != null && firstObject.equals(secondObject);
        } catch (final Exception ignore) {

        }

        if (!valid) {
            context.buildConstraintViolationWithTemplate(message)
                    .addPropertyNode(firstFieldName)
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
        }
        return valid;
    }
}
