package com.kk.validation.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "tokengen")
@Setter
@Getter
public class TokenGenConfig {
    private int lengthToken;
    private int lengthCode;
}
