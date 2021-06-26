package com.kk.validation.controller;

import com.kk.validation.domain.GenerationRequest;
import com.kk.validation.exceptions.ExceptionNotValidEmail;
import com.kk.validation.exceptions.ExceptionTypeToken;
import com.kk.validation.service.MailSenderSrv;
import com.kk.validation.service.SQLiteSrv;
import com.kk.validation.service.TokenGen;
import com.kk.validation.service.ValidationEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class RequestController {
    private final TokenGen generation;
    private final SQLiteSrv sqLite;
    private final MailSenderSrv mailService;

    @Autowired
    public RequestController(TokenGen generation,SQLiteSrv sqLite,MailSenderSrv mailService) {
        this.generation = generation;
        this.sqLite = sqLite;
        this.mailService = mailService;
    }

    @GetMapping("/test")
    public ResponseEntity<String> tes(){
        return ResponseEntity.ok("OK!");
    }

    //через JSON
    @PostMapping(path = "/generate", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> generationToken2(@RequestBody GenerationRequest generationRequest) {
        try {
            ValidationEmail vemail = new ValidationEmail(generation,sqLite,mailService).new Builder()
                .setEmail(generationRequest.getEmail())
                .validationEmail()
                .isTokenOrCode(generationRequest.getTokenType())
                .generateTokenOrCode()
                .saveEmailAndTokenCode()
                .sendMail()
                .Build();
            return new ResponseEntity(vemail.getTokenOrCode(),HttpStatus.OK);
        }catch (ExceptionNotValidEmail ex){
            return new ResponseEntity("ExceptionNotValidEmail",HttpStatus.BAD_REQUEST);
        }catch (ExceptionTypeToken ex){
            return new ResponseEntity("ExceptionTypeToken",HttpStatus.BAD_REQUEST);}
    }

    //через RequestParam
    @PostMapping("/generate")
    public ResponseEntity<String> generationToken(@RequestParam("tokenType") String tokenType, @RequestParam("email") String email){
        return new ResponseEntity("all right",HttpStatus.OK);
//              return new ResponseEntity("Error type of command",HttpStatus.BAD_REQUEST);
    }
}