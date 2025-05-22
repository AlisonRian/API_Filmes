package com.github.alisonrian.api_filmes.validation.ano;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

public class AnoValidator implements ConstraintValidator<AnoValido, Integer> {
    @Override
    public void initialize(AnoValido constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext constraintValidatorContext) {
        return value != null && value >= 1895 && value <= LocalDate.now().getYear();
    }
}
