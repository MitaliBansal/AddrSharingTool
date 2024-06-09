package com.addrsharingtool.addrsharingtool.model.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class AddAnAddressRequest {

    @JsonProperty(value = "address_line1")
    @NotBlank(message = "house no. and building name can not be blank")
    private String addressLine1;

    @JsonProperty(value = "address_line2")
    private String addressLine2;

    @JsonProperty(value = "landmark")
    private String landMark;

    @JsonProperty(value = "city")
    @NotBlank(message = "city can not be blank")
    private String city;

    @JsonProperty(value = "state")
    @NotBlank(message = "state can not be blank")
    private String state;

    @JsonProperty(value = "country")
    @NotBlank(message = "country can not be blank")
    private String country;

    @JsonProperty(value = "zipCode")
    @Size(min = 6, max = 6, message = "zipcode must contains 6 digits")
    private String zipCode;

    @JsonProperty(value = "latitude")
    @NotBlank(message = "latitude can not be blank")
    private String latitude;

    @JsonProperty(value = "longitude")
    @NotBlank(message = "longitude can not be blank")
    private String longitude;
    
}