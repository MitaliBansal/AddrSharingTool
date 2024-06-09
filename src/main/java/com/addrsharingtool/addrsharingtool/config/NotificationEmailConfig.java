package com.addrsharingtool.addrsharingtool.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "notification.email")
public class NotificationEmailConfig {

    @Value("${notification.email.sender.name}")
    private String emailSenderName;

    @Value("${notification.email.sender.address}")
    private String emailSenderAddress;

    @Value("${notification.email.subject}")
    private String notificationEmailSubject;

    @Value("${notification.email.body}")
    private String notificationEmailBody;
    
}