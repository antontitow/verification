package com.kk.validation.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;

/**
 * @author Titov 29.06.2021
 * SimpleMailMessageCfg
 */
@Configuration
@ConfigurationProperties(prefix = "simple.mail")
@Setter
@Getter
public class SimpleMailMessageCfg {
    private String from;
    private String subject;

    /**
     * getSimpleMailMessage
     *
     * @return SimpleMailMessage
     */
    public SimpleMailMessage getSimpleMailMessage() {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(from);
        simpleMailMessage.setSubject(subject);
        return simpleMailMessage;
    }
}
