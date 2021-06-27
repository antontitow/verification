package com.kk.validation.exceptions;

public class ExceptionGenerationToken extends Exception{
    public ExceptionGenerationToken(){
        super("The token is already in DB");
    }
}
