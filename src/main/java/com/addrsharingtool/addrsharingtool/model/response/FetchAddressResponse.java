package com.addrsharingtool.addrsharingtool.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FetchAddressResponse {

    @JsonProperty(value = "response_string")
    private String response;

    @JsonProperty(value = "notification_id")
    private Long notificationId;

}