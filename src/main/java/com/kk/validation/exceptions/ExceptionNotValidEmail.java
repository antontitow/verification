package com.kk.validation.exceptions;

/**
 * @author Titov 29.06.2021
 * ExceptionNotValidEmail
 */
public class ExceptionNotValidEmail extends Exception {
    public ExceptionNotValidEmail() {
        super("Invalid format email");
    }
}
