package com.addrsharingtool.addrsharingtool.model.userservice.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FetchUserRequest {

    @JsonProperty(value = "mobile_number")
    private String mobileNumber;
    
}