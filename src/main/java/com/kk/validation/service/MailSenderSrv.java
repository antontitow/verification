package com.kk.validation.service;

import com.kk.validation.config.MailConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;

@Service
public class MailSenderSrv {
    private final JavaMailSenderImpl mailSender;
    private final MailConfig mailConfig;

    @Autowired
    public MailSenderSrv(JavaMailSenderImpl mailSender, MailConfig mailConfig) {
        this.mailSender = mailSender;
        this.mailConfig = mailConfig;
    }

    public void Send(String to, String email, String token, String text) {
        // через SimpleMailMessage
//        SimpleMailMessage msg = new SimpleMailMessage();
//        msg.setText("hello: "+text);
//        msg.setFrom(mailConfig.getUsername());
//        msg.setTo(to);
//        try {
//            this.mailSender.send(msg);
//        }catch (MailException ex){
//            System.out.println(ex.getMessage());
//        }
        // через MimeMessage
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
            helper.setFrom(mailConfig.getUsername());
            helper.setTo(to);
            helper.setSubject(text);
            helper.setText("<html><body>" +
                    "Активируйте имейл по " +
                    "<a href = \"http://localhost/activation/"+email + "/"+ token+"\" >ссылке </a></br></br>"+
                     "<img src='cid:identity'></body></html>",true);
            //FileSystemResource res = new FileSystemResource(new File("C:\\Temp\\Identity-vs-Verification.jpeg"));
            helper.addInline("identity", new ClassPathResource("img/Identity-vs-Verification.jpeg"));
            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }
}
