package com.chat.app.validation;

import com.chat.app.model.enums.UserRole;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.stream.Collectors;

public class ValidRoleValidator implements ConstraintValidator<ValidRole, String> {

    private String allowedRoles;

    @Override
    public void initialize(ValidRole constraintAnnotation) {
        this.allowedRoles = Arrays.stream(UserRole.values())
                .map(Enum::name)
                .collect(Collectors.joining(", "));
        ConstraintValidator.super.initialize(constraintAnnotation);    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value == null)  return true;
        boolean isValid = Arrays.stream(UserRole.values())
                .anyMatch(role -> role.name().equals(value.toUpperCase()));

        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                            "Invalid value for Role. Allowed roles are: " + allowedRoles)
                    .addConstraintViolation();
        }
        return isValid;
    }
}
