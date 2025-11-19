package com.stock.portfolio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

   public void sendOtpToEmail(String email, String otp) {
        System.out.println("OTP successfully printed in logs instead.");
    }
}
