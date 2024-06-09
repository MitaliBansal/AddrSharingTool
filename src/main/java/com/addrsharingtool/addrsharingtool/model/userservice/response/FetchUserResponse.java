package com.addrsharingtool.addrsharingtool.model.userservice.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;
import lombok.Data;

@Data
public class FetchUserResponse {

    @JsonProperty(value = "first_name")
    private String firstName;

    @JsonProperty(value = "last_name")
    private String lastName;

    @JsonProperty(value = "dob")
    private LocalDate dob;

    @JsonProperty(value = "email_address")
    private String emailAddress;
    
    @JsonProperty(value = "mobile_number")
    private String mobileNumber;
    
}