package com.addrsharingtool.addrsharingtool.utils;

import java.io.IOException;
import java.util.Objects;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import org.springframework.web.multipart.MultipartFile;

import com.addrsharingtool.addrsharingtool.exception.BadRequestException;
import com.addrsharingtool.addrsharingtool.model.dto.AddressQRUniqueParameters;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class QRToTextExtractionUtility {

    private QRToTextExtractionUtility() {}

    public static AddressQRUniqueParameters extractUniqueInfoFromQR(MultipartFile addressQR) {
        log.debug("extracting Unique Info From QR");
        String info;

        try {
            BufferedImage bufferedImage = ImageIO.read(addressQR.getInputStream());
            LuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
            Result result = new MultiFormatReader().decode(bitmap);
            info = result.getText();
        } catch (IOException | NotFoundException ex) {
            log.error("Error while extracting info from QR : {}", ex.getMessage());
            throw new BadRequestException("Invalid QR code, please try again with valid QR");
        }
        
        if (Objects.isNull(info)) {
            log.error("info is null");
            throw new BadRequestException("Invalid QR code, please try again with valid QR");
        }
        
        String[] splitUniqueInfo = info.split("$$");
        if (splitUniqueInfo.length != 2) {
            log.error("splitUniqueInfo : {}", splitUniqueInfo.toString());
            throw new BadRequestException("Invalid QR code, please try again with valid QR");
        }
        
        return AddressQRUniqueParameters.builder()
                .addressUniqueCode(splitUniqueInfo[0])
                .mobileNumber(splitUniqueInfo[1]).build();
    }
    
}