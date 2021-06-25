package com.kk.validation.controller;

import com.kk.validation.domain.GenerationRequest;
import com.kk.validation.exceptions.ExceptionTypeToken;
import com.kk.validation.service.MailSenderSrv;
import com.kk.validation.service.SQLiteSrv;
import com.kk.validation.service.TokenGen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Email;
import java.util.Map;

@RestController
@Validated
public class RequestController {
    @Autowired
    MailSenderSrv mailService;
    @Autowired
    SQLiteSrv sqlLite;
    @Autowired
    TokenGen generation;

    @GetMapping("/test")
    public ResponseEntity<String> tes(){
        return ResponseEntity.ok("OK!");
    }

    @GetMapping("/add/{mail}")
    public ResponseEntity<String> add(@PathVariable @Email(message = "Error email format") String mail){
        if (sqlLite.saveMail(mail)){
        mailService.Send(mail);
        return ResponseEntity.ok(mail);}else
        { return ResponseEntity.badRequest().body("Error/ Look logs");}
    }

   // @GetMapping("/generate/{tokenType}/{email}")
  //  public ResponseEntity<String> generationToken ( String tokenType, String email){
//        try {
//            //Проверка имейла
//
//            // Генерация токена/кода
//            String token = generation.token.get(tokenType);
//
//
//            return new ResponseEntity(generation.token.get(tokenType), HttpStatus.ACCEPTED);
//
//
//        }catch (ExceptionTypeToken ex){
//            return new ResponseEntity("Error type of command",HttpStatus.BAD_REQUEST);
//        }
//    }

    //через JSON
    @PostMapping(path = "/generate", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> generationToken2(@RequestBody GenerationRequest generationRequest){
        try {
            //Проверка имейла
            // Генерация токена/кода
            String token = generation.token.get(generationRequest.getTokenType());
            return new ResponseEntity(generation.token.get(generationRequest.getTokenType()), HttpStatus.ACCEPTED);

        }catch (ExceptionTypeToken ex){
            return new ResponseEntity("Error type of command",HttpStatus.BAD_REQUEST);
        }
    }

    //через RequestParam
    @PostMapping("/generate")
    public ResponseEntity<String> generationToken(@RequestParam("tokenType") String tokenType, @RequestParam("email") String email){
        try {
            //Проверка имейла

            // Генерация токена/кода
            String token = generation.token.get(tokenType);
            return new ResponseEntity(generation.token.get(tokenType), HttpStatus.ACCEPTED);

        }catch (ExceptionTypeToken ex){
            return new ResponseEntity("Error type of command",HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/activate/{code}")
    public ResponseEntity<String> activate(@PathVariable String code) {
       // userSevice.activateUser(code);
        return ResponseEntity.ok("activate");
    }


}