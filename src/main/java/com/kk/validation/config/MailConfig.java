package com.kk.validation.config;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

/**
 * @author Titov 29.06.2021
 * MailConfig
 */
@Configuration
@ConfigurationProperties(prefix = "mail.config")
@Setter
@Getter
public class MailConfig {
    private String host;
    private String username;
    private String password;
    private String protocol;
    private int port;
    private String debug;
    private String ip;

    /**
     * getMailSender
     *
     * @return JavaMailSenderImpl
     */
    @Bean
    public JavaMailSenderImpl getMailSender() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost(host);
        javaMailSender.setPort(port);
        javaMailSender.setUsername(username);
        javaMailSender.setPassword(password);
        Properties properties = javaMailSender.getJavaMailProperties();
        properties.setProperty("mail.transport.protocol", protocol);
        properties.setProperty("mail.debug", debug);
        return javaMailSender;
    }
}
