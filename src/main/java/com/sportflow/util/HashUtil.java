package com.sportflow.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.mindrot.jbcrypt.BCrypt;

public class HashUtil {

    public static String hashPassword(String plainTextPassword) {
        // Use BCrypt for strong password hashing
        return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
    }

    public static boolean checkPassword(String plainTextPassword, String hashedPassword) {
        // Verify plain text password against a BCrypt hash
        return BCrypt.checkpw(plainTextPassword, hashedPassword);
    }
    //Alternative Using SHA-256 (Less Secure than BCrypt)
    public static String sha256Hash(String input) {
        return DigestUtils.sha256Hex(input);
    }
}