package com.ua.hackathon2024java.services;

import com.ua.hackathon2024java.entity.Regions;

public interface MailService {
    void sendEmail(String email, String subject, String text);
    void sendMailsByRegion(Regions region);
}
