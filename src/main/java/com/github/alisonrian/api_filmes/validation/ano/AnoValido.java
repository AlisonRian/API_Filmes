package com.github.alisonrian.api_filmes.validation.ano;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AnoValidator.class)
public @interface AnoValido {
    String message() default "Ano de lançamento inválido.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
