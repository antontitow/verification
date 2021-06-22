package com.kk.validation.controller;

import com.kk.validation.service.MailSenderSrv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RequestController {

    @Autowired
    MailSenderSrv mailService;

    @GetMapping("/test")
    public ResponseEntity<String> tes(){
        return ResponseEntity.ok("OK");
    }

    @GetMapping("/add/{mail}")
    public ResponseEntity<String> add(@PathVariable String mail){
        mailService.Send(mail);
        return ResponseEntity.ok(mail);
    }


}