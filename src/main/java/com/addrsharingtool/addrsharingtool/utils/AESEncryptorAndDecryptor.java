package com.addrsharingtool.addrsharingtool.utils;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AESEncryptorAndDecryptor {

    @Value("${aes.secret.key}")
    private final String aesSecretKey;

    private static final String ENCRYPTION_ALGORITHM = "AES";
    private static final byte[] STATIC_KEY;
    private static final SecretKey secretKey;

    public AESEncryptorAndDecryptor() {
        STATIC_KEY = aesSecretKey.getBytes();
        secretKey = new SecretKeySpec(STATIC_KEY, ENCRYPTION_ALGORITHM);
    }

    public String encryptNumber(String text) throws Exception {
        Cipher cipher = Cipher.getInstance(ENCRYPTION_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedBytes = cipher.doFinal(text.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public String decryptNumber(String encryptedNumber) throws Exception {
        Cipher cipher = Cipher.getInstance(ENCRYPTION_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] encryptedBytes = Base64.getDecoder().decode(encryptedNumber);
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
        return decryptedBytes.toString();
    }

}