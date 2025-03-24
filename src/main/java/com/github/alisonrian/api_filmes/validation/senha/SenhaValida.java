package com.github.alisonrian.api_filmes.validation.senha;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = SenhaValidator.class)
public @interface SenhaValida {
    String message() default "Senha inválida - A senha deve ter ao menos 8 caracteres";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
