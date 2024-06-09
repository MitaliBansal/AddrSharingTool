package com.addrsharingtool.addrsharingtool.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.addrsharingtool.addrsharingtool.model.response.AddressResponse;
import com.addrsharingtool.addrsharingtool.service.INotificationService;
import com.addrsharingtool.addrsharingtool.service.impl.NotificationServiceImpl;

import static com.addrsharingtool.addrsharingtool.constant.APIPath.NotificationHandler.ACCEPT_NOTIFICATION;
import static com.addrsharingtool.addrsharingtool.constant.APIPath.NotificationHandler.DECLINE_NOTIFICATION;
import static com.addrsharingtool.addrsharingtool.constant.APIPath.NotificationHandler.CHECK_NOTIFICATION_STATUS;
import static com.addrsharingtool.addrsharingtool.constant.APIResponseString.GENERAL_RESPONSE;

@RestController
public class NotificationController {

    private INotificationService notificationService;

    public NotificationController(NotificationServiceImpl notificationServiceImpl) {
        this.notificationService = notificationServiceImpl;
    }

    @PostMapping(value = ACCEPT_NOTIFICATION)
    public ResponseEntity<String> acceptNotification(@PathVariable("encrypted_text") String encryptedText) {
        notificationService.handleNotificationResponse(encryptedText, true);
        return ResponseEntity.ok().body(GENERAL_RESPONSE);
    }

    @PostMapping(value = DECLINE_NOTIFICATION)
    public ResponseEntity<String> declineNotification(@PathVariable("encrypted_text") String encryptedText) {
        notificationService.handleNotificationResponse(encryptedText, false);
        return ResponseEntity.ok().body(GENERAL_RESPONSE);
    }

    @GetMapping(value = CHECK_NOTIFICATION_STATUS)
    public ResponseEntity<AddressResponse> checkNotificationStatus(@RequestParam(value = "notification_id") Long notificationId) {
        return ResponseEntity.ok().body(notificationService.checkNotificationStatus(notificationId));
    }
    
}