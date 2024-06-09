package com.addrsharingtool.addrsharingtool.service.http;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.addrsharingtool.addrsharingtool.config.UserServiceConfig;
import com.addrsharingtool.addrsharingtool.config.CustomRestTemplate;
import com.addrsharingtool.addrsharingtool.model.userservice.request.FetchUserRequest;
import com.addrsharingtool.addrsharingtool.model.userservice.response.FetchUserResponse;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class UserService {

    private final UserServiceConfig userServiceConfig;
    private CustomRestTemplate customRestTemplate;

    public UserService(UserServiceConfig userServiceConfig, CustomRestTemplate customRestTemplate) {
        this.userServiceConfig = userServiceConfig;
        this.customRestTemplate = customRestTemplate;
    }

    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(HttpHeaders.AUTHORIZATION, );
        return headers;
    }

    public FetchUserResponse fetchUser(String mobileNumber) {
        try {
            String url = userServiceConfig.getBaseUrl() + userServiceConfig.getFetchUserApiUrl();
            HttpEntity<FetchUserRequest> httpEntity = new HttpEntity<>(FetchUserRequest.builder().mobileNumber(mobileNumber).build(), getHeaders());
            log.info("UserService.fetchUser request httpEntity : {}", httpEntity);
            ResponseEntity<FetchUserResponse> response = customRestTemplate.exchange(url, HttpMethod.GET, httpEntity, FetchUserResponse.class);
            if (response.getStatusCode() != HttpStatus.OK) {
                log.error("UserService.fetchUser api failed, response : {}", response.getBody());
                return response.getBody();
            }

            log.info("UserService.fetchUser api success, response : {}", response.getBody());
            return response.getBody();
        } catch (EntityNotFoundException ex) {
            log.error("EntityNotFoundException in UserService.fetchUser api with stacktrace : ", ex);
            return null;
        } catch (Exception ex) {
            log.error("Exception in UserService.fetchUser api with stacktrace : ", ex);
            return null;
        }
    }
    
}