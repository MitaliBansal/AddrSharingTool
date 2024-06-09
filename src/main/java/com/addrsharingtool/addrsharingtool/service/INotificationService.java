package com.addrsharingtool.addrsharingtool.service;

import com.addrsharingtool.addrsharingtool.model.response.AddressResponse;

public interface INotificationService {

    void handleNotificationResponse(String encryptedText, boolean isAccepted);

    AddressResponse checkNotificationStatus(Long notificationId);
    
}