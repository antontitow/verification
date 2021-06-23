package com.kk.validation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

@Service
public class MailSenderSrv {
    @Autowired
    private JavaMailSenderImpl mailSender;

    public void Send(String text){
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setText("hello: "+text);
        msg.setFrom("antontitow@bk.ru");
        msg.setTo(text);
        try {
            this.mailSender.send(msg);
        }catch (MailException ex){
            System.out.println(ex.getMessage());
        }
    }
}
