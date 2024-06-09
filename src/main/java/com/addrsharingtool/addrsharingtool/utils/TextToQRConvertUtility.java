package com.addrsharingtool.addrsharingtool.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class TextToQRConvertUtility {

    private TextToQRConvertUtility() {}

    public static byte[] convertTextToQR(String addressUniqueCode, String mobileNumber) {
        log.debug("Converting Text To QR for user: {}", mobileNumber);

        int width = 350;
        int height = 350;
        try {
            BufferedImage qrCodeImage = generateQRCodeImage(addressUniqueCode + "$$" + mobileNumber, width, height);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(qrCodeImage, "PNG", baos);
            return baos.toByteArray();
        } catch (WriterException | IOException ex) {
            throw new RuntimeException("Could not generate QR Code: " + ex.getMessage());
        }
    }

    private static BufferedImage generateQRCodeImage(String text, int width, int height) throws WriterException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);
        return MatrixToImageWriter.toBufferedImage(bitMatrix);
    }

}