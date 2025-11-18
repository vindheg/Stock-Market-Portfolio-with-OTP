package com.stock.portfolio.controller;

import com.stock.portfolio.service.EmailService;
import com.stock.portfolio.service.OtpService;
import com.stock.portfolio.service.UsernameEmailMapper;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UsernameEmailMapper emailMapper;
    private final OtpService otpService;
    private final EmailService emailService;

    public AuthController(UsernameEmailMapper emailMapper, OtpService otpService, EmailService emailService) {
        this.emailMapper = emailMapper;
        this.otpService = otpService;
        this.emailService = emailService;
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> req) {

        String username = req.get("username");
        String password = req.get("password");

        // ANY username + password works (your current behavior)
        String email = emailMapper.getEmailForUsername(username);

        if (email == null) {
            return Map.of("status", "error", "message", "User not found");
        }

        // Generate OTP
        String otp = otpService.generateOtp(username);

        // Send email
        emailService.sendOtpToEmail(email, otp);

        return Map.of("status", "otp_sent", "message", "OTP sent to email");
    }

    @PostMapping("/verify-otp")
    public Map<String, String> verifyOtp(@RequestBody Map<String, String> req) {

        String username = req.get("username");
        String otp = req.get("otp");

        if (otpService.validateOtp(username, otp)) {
            return Map.of("status", "success", "message", "OTP verified");
        }

        return Map.of("status", "error", "message", "Invalid OTP");
    }
}
