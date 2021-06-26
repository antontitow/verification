package com.kk.validation.controller;

import com.kk.validation.domain.GenerationRequest;
import com.kk.validation.domain.Verification;
import com.kk.validation.exceptions.ExceptionNotValidEmail;
import com.kk.validation.exceptions.ExceptionTypeToken;
import com.kk.validation.repository.VerificationRepo;
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
    private final VerificationRepo verificationEntity;

    @Autowired
    public RequestController(TokenGen generation,SQLiteSrv sqLite,MailSenderSrv mailService,VerificationRepo verificationEntity) {
        this.generation = generation;
        this.sqLite = sqLite;
        this.mailService = mailService;
        this.verificationEntity = verificationEntity;
    }

    @GetMapping("/test")
    public ResponseEntity<String> tes(){
        return ResponseEntity.ok("OK!");
    }

    @GetMapping("/activation/{mail}/{token}")
    public ResponseEntity<String> activation(@PathVariable String mail,@PathVariable String token){
//        Verification verification = verificationEntity.findByTokenCode(token));
//         if (verificationEntity.findByEmail(mail).equals(verification){
//            verification.setActive();
        if (verificationEntity.findByEmail(mail).equals(verificationEntity.findByTokenCode(token))){
        return ResponseEntity.ok("OK!");}
         return new ResponseEntity<>("Имейл не подтвержден", HttpStatus.BAD_REQUEST);
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