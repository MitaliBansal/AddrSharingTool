package com.addrsharingtool.addrsharingtool.service.impl;

import java.util.List;

import org.simplejavamail.api.email.Email;
import org.simplejavamail.api.email.Recipient;
import org.simplejavamail.api.mailer.Mailer;
import org.simplejavamail.api.mailer.config.TransportStrategy;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.MailerBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.addrsharingtool.addrsharingtool.config.EmailConfig;
import com.addrsharingtool.addrsharingtool.config.NotificationEmailConfig;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class EmailNotificationService {

    private final Mailer mailer;
    private EmailConfig emailConfig;
    private NotificationEmailConfig notificationEmailConfig;

    public EmailNotificationService(EmailConfig emailConfig, NotificationEmailConfig notificationEmailConfig) {
        this.emailConfig = emailConfig;
        this.notificationEmailConfig = notificationEmailConfig;
        this.mailer = MailerBuilder.withSMTPServer(emailConfig.getHost(), emailConfig.getPort(),
                                            emailConfig.getUsername(), emailConfig.getPassword())
                                   .withTransportStrategy(TransportStrategy.valueOf(emailConfig.getTransportStrategy())).buildMailer();
    }

    /** currently provided support for single recipient only */
    public void sendEmailNotification(List<String> recipients, String name, String subject, String messageBody) {
        try {
            Email email = EmailBuilder.startingBlank()
                .from(notificationEmailConfig.getEmailSenderName(), notificationEmailConfig.getEmailSenderAddress())
                .withSubject(subject)
                .withPlainText(messageBody)
                .withHTMLText("<html><body>" + messageBody + "</body></html>")
                .buildEmail();

            recipients.forEach(recipient -> email.addRecipient(name, recipient, Recipient.TO));

            mailer.sendMail(email);
            log.debug("Email sent successfully to the user: {}, email: {}", recipients.get(0), email);
        } catch(Exception ex) {
            log.error("Error in sending email to the user: {}, ex: {}", recipients.get(0), ex.getMessage());
        }
        
    }
    
}