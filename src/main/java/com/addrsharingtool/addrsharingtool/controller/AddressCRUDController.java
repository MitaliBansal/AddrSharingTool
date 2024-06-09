package com.addrsharingtool.addrsharingtool.controller;

import java.util.Objects;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.addrsharingtool.addrsharingtool.exception.BadRequestException;
import com.addrsharingtool.addrsharingtool.model.request.AddAnAddressRequest;
import com.addrsharingtool.addrsharingtool.model.response.FetchAddressResponse;
import com.addrsharingtool.addrsharingtool.service.IAddressCRUDService;
import com.addrsharingtool.addrsharingtool.service.impl.AddressCRUDServiceImpl;

import static com.addrsharingtool.addrsharingtool.constant.APIPath.Address.ADD_ADDRESS;
import static com.addrsharingtool.addrsharingtool.constant.APIPath.Address.FETCH_ADDRESS;

@RestController
public class AddressCRUDController {

    private IAddressCRUDService addressCRUDService;

    public AddressCRUDController(AddressCRUDServiceImpl addressCRUDServiceImpl) {
        this.addressCRUDService = addressCRUDServiceImpl;
    }

    @PostMapping(value = ADD_ADDRESS)
    public ResponseEntity<byte[]> addANewAddress(@RequestHeader("User-Data") String userData, @RequestBody AddAnAddressRequest addAnAddressRequest) {
        return ResponseEntity.ok().body(addressCRUDService.addANewAddress(userData, addAnAddressRequest));
    }

    @PostMapping(value = FETCH_ADDRESS)
    public ResponseEntity<FetchAddressResponse> fetchAnAddress(@RequestHeader("User-Data") String userData, @RequestParam(name = "address_code") MultipartFile addressQRCode) {
        if (Objects.isNull(addressQRCode) || addressQRCode.isEmpty()) {
            throw new BadRequestException("upload the appropriate address QR");
        }

        return ResponseEntity.ok().body(addressCRUDService.fetchAnAddress(userData, addressQRCode));
    }
    
}