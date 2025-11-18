package com.stock.portfolio.service;

import com.sendinblue.ApiClient;
import com.sendinblue.Configuration;
import sibApi.TransactionalEmailsApi;
import sibModel.SendSmtpEmail;
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

    public void sendOtpToEmail(String email, String otp) {
        try {
            ApiClient client = Configuration.getDefaultApiClient();
            client.setApiKey("api-key", apiKey);

            TransactionalEmailsApi apiInstance = new TransactionalEmailsApi();

            SendSmtpEmail mail = new SendSmtpEmail()
                    .sender(new SendSmtpEmail.Sender()
                            .email(senderEmail)
                            .name(senderName))
                    .to(java.util.Collections.singletonList(
                            new SendSmtpEmail.To().email(email)))
                    .subject("Your OTP Code")
                    .htmlContent("<h2>Your OTP is: <b>" + otp + "</b></h2>");

            apiInstance.sendTransacEmail(mail);
            System.out.println("OTP Email Sent Successfully!");

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to send OTP email: " + e.getMessage());
        }
    }
}
