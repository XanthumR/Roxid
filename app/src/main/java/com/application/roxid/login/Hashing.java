package com.application.roxid.login;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hashing {

    public static String hashPassword(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-512");


            byte[] hashedBytes = digest.digest(input.getBytes());


            StringBuilder hexString = new StringBuilder();
            for (byte b : hashedBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-512 doesn't support.", e);
        }
    }

}

