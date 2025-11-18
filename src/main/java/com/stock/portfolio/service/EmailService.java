package com.stock.portfolio.service;

import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Value("${brevo.api.key}")
    private String apiKey;

    @Value("${brevo.sender.email}")
    private String senderEmail;

    @Value("${brevo.sender.name}")
    private String senderName;

    private final OkHttpClient client = new OkHttpClient();

    public void sendOtpToEmail(String email, String otp) {
        try {
            String jsonBody = """
                {
                  "sender": { "email": "%s", "name": "%s" },
                  "to": [{ "email": "%s" }],
                  "subject": "Your OTP Code",
                  "htmlContent": "<h2>Your OTP is <b>%s</b></h2>"
                }
                """.formatted(senderEmail, senderName, email, otp);

            Request request = new Request.Builder()
                    .url("https://api.brevo.com/v3/smtp/email")
                    .addHeader("api-key", apiKey)
                    .addHeader("Content-Type", "application/json")
                    .post(RequestBody.create(jsonBody, MediaType.parse("application/json")))
                    .build();

            Response response = client.newCall(request).execute();
            System.out.println("Email API response: " + response.code());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
