package com.github.alisonrian.api_filmes.validation.senha;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class SenhaValidator implements ConstraintValidator<SenhaValida, String> {
    @Override
    public void initialize(SenhaValida constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s == null || s.isBlank()) {
            setMensagemErro(constraintValidatorContext, "A senha não pode estar vazia");
            return false;
        }
        if (s.length() < 8) {
            setMensagemErro(constraintValidatorContext, "A senha deve ter pelo menos 8 caracteres");
            return false;
        }
        if (!s.matches(".*[A-Z].*")) {
            setMensagemErro(constraintValidatorContext, "A senha deve conter pelo menos uma letra maiúscula");
            return false;
        }
        if (!s.matches(".*\\d.*")) {
            setMensagemErro(constraintValidatorContext, "A senha deve conter pelo menos um número");
            return false;
        }
        if (!s.matches(".*[!@#$%^&*()].*")) {
            setMensagemErro(constraintValidatorContext, "A senha deve conter pelo menos um caractere especial (!@#$%^&*())");
            return false;
        }

        return true;
//        return s.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,20}$");
    }

    private void setMensagemErro(ConstraintValidatorContext context, String mensagem) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(mensagem).addConstraintViolation();
    }
}
