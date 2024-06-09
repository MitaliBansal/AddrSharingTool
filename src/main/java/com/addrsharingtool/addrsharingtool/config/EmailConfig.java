package com.addrsharingtool.addrsharingtool.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "smtp")
public class EmailConfig {

    @Value("${smtp.host}")
    private String host;

    @Value("${smtp.port}")
    private Integer port;

    @Value("${smtp.username}")
    private String username;

    @Value("${smtp.password}")
    private String password;

    @Value("${smtp.transport.strategy}")
    private String transportStrategy;
    
}