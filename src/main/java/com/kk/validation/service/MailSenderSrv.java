package com.kk.validation.service;

import com.kk.validation.config.MailConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

@Service
public class MailSenderSrv {
    private String s;
    @Autowired
    private JavaMailSenderImpl mailSender;
    @Autowired
    private SimpleMailMessage simpleMailMessage;

    public void Send(String text){
        SimpleMailMessage msg = new SimpleMailMessage(this.simpleMailMessage);
        msg.setText("hello: "+text);
        msg.setTo(text);
        try {
            this.mailSender.send(msg);
        }catch (MailException ex){
            System.out.println(ex.getMessage());
        }
    }
}
