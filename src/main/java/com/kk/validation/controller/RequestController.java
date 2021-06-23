package com.kk.validation.controller;

import com.kk.validation.service.MailSenderSrv;
import com.kk.validation.service.SQLiteSrv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Email;

@RestController
@Validated
public class RequestController {

    @Autowired
    MailSenderSrv mailService;
    @Autowired
    SQLiteSrv sqlLite;

    @GetMapping("/test")
    public ResponseEntity<String> tes(){
        return ResponseEntity.ok("OK");
    }

    @GetMapping("/add/{mail}")
    public ResponseEntity<String> add(@PathVariable @Email(message = "Error email format") String mail){
        if (sqlLite.saveMail(mail)){
        mailService.Send(mail);
        return ResponseEntity.ok(mail);}else
        { return ResponseEntity.badRequest().body("Error/ Look logs");}
    }

}