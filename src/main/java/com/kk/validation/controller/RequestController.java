package com.kk.validation.controller;

import com.kk.validation.domain.GenerationRequest;
import com.kk.validation.domain.Verification;
import com.kk.validation.exceptions.ExceptionGenerationToken;
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

    //через JSON
    @PostMapping(path = "/generate", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> generationToken2(@RequestBody GenerationRequest generationRequest) {
        try {
            ValidationEmail vemail = new ValidationEmail(generation,sqLite,mailService).new Builder()
                .setEmail(generationRequest.getEmail())
                .validationEmail()
                .isTokenOrCode(generationRequest.getTokenType())
                .generateTokenOrCode()
                .existTokeninDB()
                .saveEmailAndTokenCode()
                .sendMail()
                .Build();
            return new ResponseEntity(vemail.getTokenOrCode(),HttpStatus.OK);
        }catch (ExceptionNotValidEmail ex){
            return new ResponseEntity("ExceptionNotValidEmail",HttpStatus.BAD_REQUEST);
        }catch (ExceptionTypeToken ex){
            return new ResponseEntity("ExceptionTypeToken",HttpStatus.BAD_REQUEST);
        }catch (ExceptionGenerationToken ex){
            return new ResponseEntity("ExceptionDuplicateToken",HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/activation/{email}/{tokenType}")
    public ResponseEntity<String> activation(@PathVariable String email,@PathVariable String token){
        Verification row;
        try {
            row  = verificationEntity.findByEmail(email);
            if (row.equals(null)) throw new NullPointerException();
        }catch (NullPointerException npex){
            return new ResponseEntity("Error authentification",HttpStatus.BAD_REQUEST);
        }
            if (row.getTokenCode().equals(token)){
                row.setActive();
                row.setRevision();
                verificationEntity.save(row);
                return new ResponseEntity("Код подтвержден",HttpStatus.OK);
            }else{row.setRevision();
                verificationEntity.save(row);
                return new ResponseEntity("Error authentification",HttpStatus.BAD_REQUEST);
            }
//              return new ResponseEntity("Error type of command",HttpStatus.BAD_REQUEST);
    }
}