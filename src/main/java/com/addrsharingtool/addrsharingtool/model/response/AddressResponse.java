package com.addrsharingtool.addrsharingtool.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddressResponse {

    @JsonProperty(value = "address_line1")
    private String addressLine1; //flat No./house No., floor No., Building Name
    
    @JsonProperty(value = "address_line2")
    private String addressLine2; //street Address

    @JsonProperty(value = "landmark")
    private String landmark;

    @JsonProperty(value = "city")
    private String city;

    @JsonProperty(value = "zipCode")
    private String zipCode;

    @JsonProperty(value = "state")
    private String state;

    @JsonProperty(value = "country")
    private String country;

    @JsonProperty(value = "latitude")
    private String latitude;

    @JsonProperty(value = "longitude")
    private String longitude;

    @JsonProperty(value = "accepted")
    private Boolean accepted;
    
}