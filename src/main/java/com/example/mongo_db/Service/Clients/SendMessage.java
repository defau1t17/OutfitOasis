package com.example.mongo_db.Service.Clients;


import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class SendMessage {


    public static void sendRecoveryPassword(String receiver, String sender, String generatedRecoveryCode, JavaMailSender javaMailSender) {
        SimpleMailMessage recoveryMessage = new SimpleMailMessage();
        recoveryMessage.setFrom(sender);
        recoveryMessage.setTo(receiver);
        recoveryMessage.setSubject("Shop password recovery");
        recoveryMessage.setText("Your personal recovery code is " + generatedRecoveryCode);
        javaMailSender.send(recoveryMessage);
    }




}
