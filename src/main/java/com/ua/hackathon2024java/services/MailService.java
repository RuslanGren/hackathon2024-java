package com.ua.hackathon2024java.services;

public interface MailService {
    void sendVerificationEmail(String email, String subject, String text);
}
