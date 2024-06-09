package com.addrsharingtool.addrsharingtool.service.impl;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.addrsharingtool.addrsharingtool.dao.mysql.entity.UserAddressMapping;
import com.addrsharingtool.addrsharingtool.config.NotificationEmailConfig;
import com.addrsharingtool.addrsharingtool.constant.NotificationChannel;
import com.addrsharingtool.addrsharingtool.dao.mysql.entity.NotificationAudit;
import com.addrsharingtool.addrsharingtool.dao.mysql.repository.UserAddressMappingRepository;
import com.addrsharingtool.addrsharingtool.dao.mysql.repository.NotificationAuditRepository;
import com.addrsharingtool.addrsharingtool.exception.BadRequestException;
import com.addrsharingtool.addrsharingtool.exception.UserServiceException;
import com.addrsharingtool.addrsharingtool.model.dto.AddressQRUniqueParameters;
import com.addrsharingtool.addrsharingtool.model.dto.User;
import com.addrsharingtool.addrsharingtool.model.request.AddAnAddressRequest;
import com.addrsharingtool.addrsharingtool.model.response.FetchAddressResponse;
import com.addrsharingtool.addrsharingtool.model.userservice.response.FetchUserResponse;
import com.addrsharingtool.addrsharingtool.service.IAddressCRUDService;
import com.addrsharingtool.addrsharingtool.service.http.UserService;
import com.addrsharingtool.addrsharingtool.utils.AESEncryptorAndDecryptor;
import com.addrsharingtool.addrsharingtool.utils.EncryptDataUsingSHA256;
import com.addrsharingtool.addrsharingtool.utils.QRToTextExtractionUtility;
import com.addrsharingtool.addrsharingtool.utils.TextToQRConvertUtility;

import lombok.extern.log4j.Log4j2;
import static com.addrsharingtool.addrsharingtool.utils.ValidateAndFetchUserData.validateAndFetchUserData;
import static com.addrsharingtool.addrsharingtool.constant.APIPath.NotificationHandler.ACCEPT_NOTIFICATION;
import static com.addrsharingtool.addrsharingtool.constant.APIPath.NotificationHandler.DECLINE_NOTIFICATION;
import static com.addrsharingtool.addrsharingtool.constant.APIResponseString.FETCH_ADDRESS_API_RESPONSE_STRING;

@Log4j2
@Service
public class AddressCRUDServiceImpl implements IAddressCRUDService {

    private UserAddressMappingRepository userAddressMappingRepository;
    private NotificationAuditRepository notificationAuditRepository;
    private EmailNotificationService emailNotificationService;
    private AESEncryptorAndDecryptor aesEncryptorAndDecryptor;
    private NotificationEmailConfig notificationEmailConfig;
    private UserService userService;

    public AddressCRUDServiceImpl(UserAddressMappingRepository userAddressMappingRepository,
                                  NotificationAuditRepository notificationAuditRepository,
                                  EmailNotificationService emailNotificationService,
                                  AESEncryptorAndDecryptor aesEncryptorAndDecryptor, 
                                  NotificationEmailConfig notificationEmailConfig, UserService userService) {
        this.userAddressMappingRepository = userAddressMappingRepository;
        this.notificationAuditRepository = notificationAuditRepository;
        this.emailNotificationService = emailNotificationService;
        this.aesEncryptorAndDecryptor = aesEncryptorAndDecryptor;
        this.notificationEmailConfig = notificationEmailConfig;
        this.userService = userService;
    }

    public byte[] addANewAddress(String userData, AddAnAddressRequest addAnAddressRequest) {
        log.debug("Received request for adding a new address: {}, by user: {}", addAnAddressRequest, userData);
        User user = validateAndFetchUserData(userData);
        
        UserAddressMapping userAddressMapping = UserAddressMapping.builder()
            .userId(user.getId())
            .addressLine1(addAnAddressRequest.getAddressLine1())
            .addressLine2(addAnAddressRequest.getAddressLine2())
            .landmark(addAnAddressRequest.getLandMark())
            .city(addAnAddressRequest.getCity())
            .state(addAnAddressRequest.getState())
            .zipCode(addAnAddressRequest.getZipCode())
            .country(addAnAddressRequest.getCountry())
            .latitude(addAnAddressRequest.getLatitude())
            .longitude(addAnAddressRequest.getLongitude()).build();

        String addressUniqueCode = EncryptDataUsingSHA256.hashWithSHA256(userAddressMapping.toString());
        userAddressMapping.setAddressUniqueCode(addressUniqueCode);
        userAddressMappingRepository.save(userAddressMapping);
        return TextToQRConvertUtility.convertTextToQR(addressUniqueCode, user.getMobileNumber());
    }

    public FetchAddressResponse fetchAnAddress(String userData, MultipartFile addressQRCode) {
        log.debug("Received request for fetching an address by userId: {}, size of QR code: {}", userData, addressQRCode.getSize());
        User user = validateAndFetchUserData(userData);

        AddressQRUniqueParameters addressQRUniqueParameters = QRToTextExtractionUtility.extractUniqueInfoFromQR(addressQRCode);
        Optional<UserAddressMapping> userAddressMappingOptional = userAddressMappingRepository.findByAddressUniqueCode(addressQRUniqueParameters.getAddressUniqueCode());
        if (!userAddressMappingOptional.isPresent()) {
            log.error("userAddressMapping not present");
            throw new BadRequestException("Invalid QR code");
        }

        FetchUserResponse fetchUserResponse = userService.fetchUser(addressQRUniqueParameters.getMobileNumber());
        if (Objects.isNull(fetchUserResponse)) {
            throw new UserServiceException("BadRequest/Internal Server Error in fetching user address, please try again later");
        }

        NotificationAudit notificationAudit = notificationAuditRepository.save(NotificationAudit.builder()
                    .userId(user.getId())
                    .notifiedUserId(userAddressMappingOptional.get().getUserId()).build());

        CompletableFuture.runAsync(() -> {
            String encryptedTextForEmail = aesEncryptorAndDecryptor.encryptNumber(notificationAudit.getId().toString() + "$$" + NotificationChannel.EMAIL);
            String emailBody = notificationEmailConfig.getNotificationEmailBody().replace("{username}", user.getName())
                                        .replace("{accept_link}", + ACCEPT_NOTIFICATION.replace("{encrypted_text}", encryptedTextForEmail))
                                        .replace("{deny_link}", + DECLINE_NOTIFICATION.replace("{encrypted_text}", encryptedTextForEmail));
            emailNotificationService.sendEmailNotification(Arrays.asList(fetchUserResponse.getEmailAddress()), user.getName(), notificationEmailConfig.getNotificationEmailSubject(), emailBody);
        });

        return FetchAddressResponse.builder().notificationId(notificationAudit.getId())
                            .response(FETCH_ADDRESS_API_RESPONSE_STRING).build();
    }
    
}