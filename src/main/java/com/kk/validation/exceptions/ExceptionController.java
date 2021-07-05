package com.kk.validation.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * ExceptionController
 * Titov A.R. 02.07.2021
 */
@ControllerAdvice
public class ExceptionController {
    @ExceptionHandler({MethodArgumentNotValidException.class})
    protected ResponseEntity<String> onTokenType(MethodArgumentNotValidException MNV, WebRequest request){
        List<ObjectError> listobjectError = MNV.getAllErrors();
        ObjectError objectError = listobjectError.get(listobjectError.size()-1);
        return new ResponseEntity<String>(objectError.getDefaultMessage(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler({NoSuchElementException.class})
    protected ResponseEntity<String> onAbsentId(NoSuchElementException MNV, WebRequest request){
        return new ResponseEntity<String>(MNV.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
