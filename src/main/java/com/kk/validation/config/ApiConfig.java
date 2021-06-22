package com.kk.validation.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"com.kk.validation.controller,com.kk.validation.service"})
public class ApiConfig {
}
