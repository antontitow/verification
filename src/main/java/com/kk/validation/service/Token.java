package com.kk.validation.service;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Token
 * @author Titov A.R. 02.07.2021
 */
@Documented
@Target({FIELD,PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = {TokenValidator.class})
public @interface Token {

    String message() default "{*.validation.constraint.Enum.message}";
    Class<? extends Payload>[] payload() default {};
    Class<?>[] groups() default {};

    /**
     * type of token
     */
    String[] tokentype () ;


    /**
     * the enum's class-type
     *
     * @return Class
     */
}