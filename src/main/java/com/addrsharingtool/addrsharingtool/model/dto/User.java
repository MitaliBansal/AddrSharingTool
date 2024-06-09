package com.addrsharingtool.addrsharingtool.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class User {

    @JsonProperty(value = "id")
    private Long id;

    @JsonProperty(value = "name")
    private String name;
    
    @JsonProperty(value = "mobile_number")
    private String mobileNumber;

}