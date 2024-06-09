package com.addrsharingtool.addrsharingtool.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncryptDataUsingSHA256 {

    private EncryptDataUsingSHA256() {}

    public static String hashWithSHA256(String text) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedhash = digest.digest(text.getBytes());
            return bytesToHex(encodedhash);
        } catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException(ex);
        }
    }

    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        
        return hexString.toString();
    }
    
}