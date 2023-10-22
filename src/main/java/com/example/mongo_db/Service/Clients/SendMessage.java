package com.example.mongo_db.Service.Clients;


import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class SendMessage {


    public static void sendRecoveryPassword(String receiver, String sender, String generatedRecoveryCode, JavaMailSender javaMailSender) {
        SimpleMailMessage recoveryMessage = new SimpleMailMessage();
        recoveryMessage.setFrom(sender);
        recoveryMessage.setTo(receiver);
        recoveryMessage.setSubject("OutFitOasis account password recovery");
        recoveryMessage.setText("Your personal recovery code is " + generatedRecoveryCode);
        javaMailSender.send(recoveryMessage);
    }

    public static void sendMessageForNewClientsRole(String receiver, String sender, JavaMailSender javaMailSender) {
        SimpleMailMessage recoveryMessage = new SimpleMailMessage();
        recoveryMessage.setFrom(sender);
        recoveryMessage.setTo(receiver);
        recoveryMessage.setSubject("New Role in OutFitOasis!");
        recoveryMessage.setText("Hello, Producer! Congratulations! Now you have a producer status. Keep going! \nAdministration of OutFitOasis.");
        javaMailSender.send(recoveryMessage);
    }

    public static void sendDenyForNewRoleToClient(String receiver, String sender, JavaMailSender javaMailSender) {
        SimpleMailMessage recoveryMessage = new SimpleMailMessage();
        recoveryMessage.setFrom(sender);
        recoveryMessage.setTo(receiver);
        recoveryMessage.setSubject("New Role in OutFitOasis");
        recoveryMessage.setText("Hi there! We have given you a very careful consideration and have come to the conclusion that we are not prepared to allow you to become a producer! \nAdministration of OutFitOasis.");
        javaMailSender.send(recoveryMessage);
    }


}
