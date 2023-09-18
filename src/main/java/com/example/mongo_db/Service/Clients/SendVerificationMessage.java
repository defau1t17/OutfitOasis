package com.example.mongo_db.Service.Clients;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class SendVerificationMessage {
    public static void SendVerificationCode(String receiver, String sender, String generatedRecoveryCode, JavaMailSender javaMailSender) {
        SimpleMailMessage recoveryMessage = new SimpleMailMessage();
        recoveryMessage.setFrom(sender);
        recoveryMessage.setTo(receiver);
        recoveryMessage.setSubject("Shop account verification code");
        recoveryMessage.setText("Your personal account verification code is " + generatedRecoveryCode);
        javaMailSender.send(recoveryMessage);
    }
}
