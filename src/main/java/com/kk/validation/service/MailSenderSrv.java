package com.kk.validation.service;

import com.kk.validation.config.MailConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

@Service
public class MailSenderSrv {
    private final JavaMailSenderImpl mailSender;
    private final MailConfig mailConfig;

    @Autowired
    public MailSenderSrv(JavaMailSenderImpl mailSender, MailConfig mailConfig) {
        this.mailSender = mailSender;
        this.mailConfig = mailConfig;
    }

    public void Send(String text){
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setText("hello: "+text);
        msg.setFrom(mailConfig.getUsername());
        msg.setTo(text);
        try {
            this.mailSender.send(msg);
        }catch (MailException ex){
            System.out.println(ex.getMessage());
        }
    }
}
