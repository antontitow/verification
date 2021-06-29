package com.kk.validation.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author Titov 29.06.2021
 * TokenGenConfig
 */
@Configuration
@ConfigurationProperties(prefix = "tokengen")
@Setter
@Getter
public class TokenGenConfig {
    private int lengthToken;
    private int lengthCode;
}
