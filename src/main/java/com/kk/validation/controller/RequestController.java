package com.kk.validation.controller;

import com.kk.validation.domain.GenerationRequest;
import com.kk.validation.exceptions.ExceptionNotValidEmail;
import com.kk.validation.exceptions.ExceptionTypeToken;
import com.kk.validation.service.TokenGen;
import com.kk.validation.service.ValidationEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class RequestController {
    private final TokenGen generation;
    private final ValidationEmail validationEmail;
    @Autowired
    public RequestController(ValidationEmail validationEmail,TokenGen generation) {
        this.validationEmail = validationEmail;
        this.generation = generation;
    }

    @GetMapping("/test")
    public ResponseEntity<String> tes(){
        return ResponseEntity.ok("OK!");
    }

    //через JSON
    @PostMapping(path = "/generate", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> generationToken2(@RequestBody GenerationRequest generationRequest) throws ExceptionNotValidEmail, ExceptionTypeToken {

        ValidationEmail vemail = new ValidationEmail(generation).new Builder()
                .setEmail(generationRequest.getEmail())
                .validationEmail()
                .isTokenOrCode(generationRequest.getTokenType())
                .generateTokenOrCode()
                .Build();
        return new ResponseEntity(vemail.getTokenOrCode(),HttpStatus.OK);
//            return new ResponseEntity("Error type of command",HttpStatus.BAD_REQUEST);
    }

    //через RequestParam
    @PostMapping("/generate")
    public ResponseEntity<String> generationToken(@RequestParam("tokenType") String tokenType, @RequestParam("email") String email){
        return new ResponseEntity("all right",HttpStatus.OK);
//              return new ResponseEntity("Error type of command",HttpStatus.BAD_REQUEST);
    }
}