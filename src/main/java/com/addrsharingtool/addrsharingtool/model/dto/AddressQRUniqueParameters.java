package com.addrsharingtool.addrsharingtool.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddressQRUniqueParameters {

    private String addressUniqueCode;
    private String mobileNumber;
    
}