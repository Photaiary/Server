package com.photaiary.Photaiary.utils.config;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JasyptConfigTest {

    @Test
    void jasypt(){
        String accessKey = "AKIAWSQ2JLVRFT2ZD6GH";
        String secretKey = "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";

        String encryptAccessKey = jasyptEncrypt(accessKey);
        String encryptSecretKey = jasyptEncrypt(secretKey);

        System.out.println("encryptAccessKey : " + encryptAccessKey);
        System.out.println("encryptSecretKey : " + encryptSecretKey);
    }

    private String jasyptEncrypt(String input) {
        String key = "1234";
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setAlgorithm("PBEWithMD5AndDES");
        encryptor.setPassword(key);
        return encryptor.encrypt(input);
    }

    private String jasyptDecryt(String input){
        String key = "1234";
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setAlgorithm("PBEWithMD5AndDES");
        encryptor.setPassword(key);
        return encryptor.decrypt(input);
    }
}