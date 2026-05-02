package com.onlineakimbank.notificationservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {

    private final TwilioSmsService smsService;
    private final EmailService emailService;

    public void sendSms(String phoneNumber, String message) {
        if (phoneNumber != null && !phoneNumber.isEmpty()) {
            try {
                smsService.sendSms(phoneNumber, message);
                log.info("SMS sent to: {}", phoneNumber);
            } catch (Exception e) {
                log.error("Failed to send SMS to {}: {}", phoneNumber, e.getMessage());
            }
        } else {
            log.warn("No phone number provided for SMS");
        }
    }

    public void sendEmail(String email, String subject, String body) {
        if (email != null && !email.isEmpty()) {
            try {
                emailService.sendEmail(email, subject, body);
                log.info("Email sent to: {}", email);
            } catch (Exception e) {
                log.error("Failed to send email to {}: {}", email, e.getMessage());
            }
        } else {
            log.warn("No email address provided");
        }
    }
}