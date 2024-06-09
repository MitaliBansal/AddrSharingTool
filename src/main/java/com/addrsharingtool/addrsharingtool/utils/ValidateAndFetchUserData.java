package com.addrsharingtool.addrsharingtool.utils;

import org.apache.commons.lang3.StringUtils;

import com.addrsharingtool.addrsharingtool.exception.UnAuthorizedRequestException;
import com.addrsharingtool.addrsharingtool.model.dto.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ValidateAndFetchUserData {

    private ValidateAndFetchUserData() {}
 
    public static User validateAndFetchUserData(String userData) {
        if (StringUtils.isEmpty(userData)) {
            throw new UnAuthorizedRequestException("userData not present, invalid request");
        }

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(userData, User.class);
        } catch (JsonProcessingException ex) {
            throw new UnAuthorizedRequestException("userData is not valid, ex: " + ex.getMessage());
        }
    }
    
}