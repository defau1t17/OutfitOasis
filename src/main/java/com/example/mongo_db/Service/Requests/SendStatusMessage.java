package com.example.mongo_db.Service.Requests;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class SendStatusMessage {

    public static void SendModerationMessage(String receiver, String sender, JavaMailSender javaMailSender) {
        SimpleMailMessage status = new SimpleMailMessage();
        status.setFrom(sender);
        status.setTo(receiver);
        status.setSubject("Shop producer status");
        status.setText("Your request has been added to waiting list...Wait for response");
        javaMailSender.send(status);
    }
}
