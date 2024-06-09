package com.addrsharingtool.addrsharingtool.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "user.service")
public class UserServiceConfig {

    @Value("${user.service.baseUrl}")
    private String baseUrl;

    @Value("${user.service.fetchUserApi}")
    private String fetchUserApiUrl;
    
}