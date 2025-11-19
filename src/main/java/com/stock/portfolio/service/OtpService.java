package com.stock.portfolio.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class OtpService {

    private final Map<String, String> otpStore = new HashMap<>();

    public String generateOtp(String username) {
        String otp = String.format("%06d", new Random().nextInt(999999));
        otpStore.put(username, otp);
        System.out.println("Otp for user: "+username+"="+otp);
        return otp;
    }

    public boolean validateOtp(String username, String otp) {
        return otpStore.containsKey(username) && otpStore.get(username).equals(otp);
    }
}
