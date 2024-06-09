package com.addrsharingtool.addrsharingtool.service;

import org.springframework.web.multipart.MultipartFile;

import com.addrsharingtool.addrsharingtool.model.request.AddAnAddressRequest;
import com.addrsharingtool.addrsharingtool.model.response.FetchAddressResponse;

public interface IAddressCRUDService {

    byte[] addANewAddress(String userData, AddAnAddressRequest addAnAddressRequest);
    
    FetchAddressResponse fetchAnAddress(String userData, MultipartFile addressQRCode);
    
}