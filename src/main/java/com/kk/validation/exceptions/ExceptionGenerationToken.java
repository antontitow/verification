package com.kk.validation.exceptions;

/**
 * @author Titov 29.06.2021
 * ExceptionGenerationToken
 */
public class ExceptionGenerationToken extends Exception {
    public ExceptionGenerationToken() {
        super("The token is already in DB");
    }
}
