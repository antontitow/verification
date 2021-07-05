package com.kk.validation.service;

import com.kk.validation.exceptions.ExceptionTypeToken;
import lombok.SneakyThrows;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

/**
 * @author Titov A.R. 02.07.2021
 * TokenValidator
 */
public class TokenValidator implements ConstraintValidator<Token, String> {

    private Token token;

    /**
     * initialize
     * @param token
     */
    @Override
    public void initialize(Token token) {
        this.token = token;
    }

    /**
     * isValid
     * @param value - field value
     * @param context
     * @return
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

            if (Arrays.asList(token.tokentype()).contains(value)) {
                return true;
            }
            return false;
    }
}