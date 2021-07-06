package com.kk.validation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Mail verification
 *
 * @author Titov 29.06.2021
 */
@SpringBootApplication
@ConfigurationPropertiesScan("com.kk.validation.config")
@EnableScheduling
public class ValidationApplication {
    public static void main(String[] args) {
        SpringApplication.run(ValidationApplication.class, args);
    }
}
