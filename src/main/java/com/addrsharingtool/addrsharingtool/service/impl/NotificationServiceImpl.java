package com.addrsharingtool.addrsharingtool.service.impl;

import java.util.Objects;

import org.springframework.stereotype.Service;

import com.addrsharingtool.addrsharingtool.constant.NotificationChannel;
import com.addrsharingtool.addrsharingtool.dao.mysql.entity.NotificationAudit;
import com.addrsharingtool.addrsharingtool.dao.mysql.entity.UserAddressMapping;
import com.addrsharingtool.addrsharingtool.dao.mysql.repository.NotificationAuditRepository;
import com.addrsharingtool.addrsharingtool.dao.mysql.repository.UserAddressMappingRepository;
import com.addrsharingtool.addrsharingtool.exception.UnAuthorizedRequestException;
import com.addrsharingtool.addrsharingtool.model.response.AddressResponse;
import com.addrsharingtool.addrsharingtool.service.INotificationService;
import com.addrsharingtool.addrsharingtool.utils.AESEncryptorAndDecryptor;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class NotificationServiceImpl implements INotificationService {

    private NotificationAuditRepository notificationAuditRepository;
    private UserAddressMappingRepository userAddressMappingRepository;
    private AESEncryptorAndDecryptor aesEncryptorAndDecryptor;

    public NotificationServiceImpl(NotificationAuditRepository notificationAuditRepository,
                                   UserAddressMappingRepository userAddressMappingRepository,
                                   AESEncryptorAndDecryptor aesEncryptorAndDecryptor) {
        this.notificationAuditRepository = notificationAuditRepository;
        this.userAddressMappingRepository = userAddressMappingRepository;
        this.aesEncryptorAndDecryptor = aesEncryptorAndDecryptor;
    }

    public void handleNotificationResponse(String encryptedText, boolean isAccepted) {
        log.debug("Received request for handling notification response, encryptedText: {}, accept: {}", encryptedText, isAccepted);
        try {
            String decryptedText = aesEncryptorAndDecryptor.decryptNumber(encryptedText);
            String[] splitDecryptedText = decryptedText.split("$$");
            NotificationAudit notificationAudit = notificationAuditRepository.getReferenceById(Long.parseLong(splitDecryptedText[0]));
            notificationAudit.setNotificationAcceptanceChannel(NotificationChannel.valueOf(splitDecryptedText[1]));
            notificationAudit.setNotificationAccepted(isAccepted);
            notificationAuditRepository.save(notificationAudit);
        } catch(Exception ex) {
            log.error("request is corrupted, encryptedText: {}, ex: {}", encryptedText, ex.getMessage());
            throw new UnAuthorizedRequestException("request is corrupted ");
        }
    }

    public AddressResponse checkNotificationStatus(Long notificationId) {
        log.debug("Check notification status for notificationId: {}", notificationId);
        NotificationAudit notificationAudit = notificationAuditRepository.getReferenceById(notificationId);
        if (Objects.nonNull(notificationAudit.getNotificationAccepted())) {
            if (notificationAudit.getNotificationAccepted()) {
                UserAddressMapping userAddressMapping = userAddressMappingRepository.findByUserId(notificationAudit.getNotifiedUserId());
                return AddressResponse.builder().accepted(true)
                        .addressLine1(userAddressMapping.getAddressLine1())
                        .addressLine2(userAddressMapping.getAddressLine2())
                        .landmark(userAddressMapping.getLandmark())
                        .city(userAddressMapping.getCity())
                        .state(userAddressMapping.getState())
                        .country(userAddressMapping.getCountry())
                        .zipCode(userAddressMapping.getZipCode())
                        .latitude(userAddressMapping.getLatitude())
                        .longitude(userAddressMapping.getLongitude()).build();
            } else {
                return AddressResponse.builder().accepted(false).build();
            }
        }
        
        return AddressResponse.builder().build();
    }
    
}