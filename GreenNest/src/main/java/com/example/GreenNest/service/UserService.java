package com.example.GreenNest.service;

import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class UserService {

    public String doHash(String password){
        try{
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(password.getBytes());
            byte [] hashedByte = messageDigest.digest();
            StringBuilder stringBuilder = new StringBuilder();
            for(byte b:hashedByte){
                stringBuilder.append(String.format("%02x", b));
            }
            return stringBuilder.toString();
        }catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        return "";
    }

}
