package com.example.mongo_db.Service;


import com.example.mongo_db.Entity.OutfitOasisConst;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
public class MessageSenderService {

    public void sendRecoveryPasswordMessage(String receiver, String generatedRecoveryCode, JavaMailSender javaMailSender) {
        SimpleMailMessage recoveryMessage = new SimpleMailMessage();
        recoveryMessage.setFrom(OutfitOasisConst.OUTFIT_OASIS_SENDER);
        recoveryMessage.setTo(receiver);
        recoveryMessage.setSubject("OutFitOasis account password recovery");
        recoveryMessage.setText("Your personal recovery code is " + generatedRecoveryCode);
        javaMailSender.send(recoveryMessage);
    }

    public void sendClientNotificationAboutNewRole(String receiver, JavaMailSender javaMailSender) {
        SimpleMailMessage recoveryMessage = new SimpleMailMessage();
        recoveryMessage.setFrom(OutfitOasisConst.OUTFIT_OASIS_SENDER);
        recoveryMessage.setTo(receiver);
        recoveryMessage.setSubject("New Role in OutFitOasis!");
        recoveryMessage.setText("Hello, Producer! Congratulations! Now you have a producer status. Keep going! \nAdministration of OutFitOasis.");
        javaMailSender.send(recoveryMessage);
    }

    public void sendClientNotificationAboutRejectionNewRole(String receiver, JavaMailSender javaMailSender) {
        SimpleMailMessage recoveryMessage = new SimpleMailMessage();
        recoveryMessage.setFrom(OutfitOasisConst.OUTFIT_OASIS_SENDER);
        recoveryMessage.setTo(receiver);
        recoveryMessage.setSubject("New Role in OutFitOasis");
        recoveryMessage.setText("Hi there! We have given you a very careful consideration and have come to the conclusion that we are not prepared to allow you to become a producer! \nAdministration of OutFitOasis.");
        javaMailSender.send(recoveryMessage);
    }

    public void sendClientVerificationMessage(String receiver, String generatedRecoveryCode, JavaMailSender javaMailSender) {
        SimpleMailMessage recoveryMessage = new SimpleMailMessage();
        recoveryMessage.setFrom(OutfitOasisConst.OUTFIT_OASIS_SENDER);
        recoveryMessage.setTo(receiver);
        recoveryMessage.setSubject("Shop account verification code");
        recoveryMessage.setText("Your personal account verification code is " + generatedRecoveryCode);
        javaMailSender.send(recoveryMessage);
    }

    public void sendClientNotificationAboutRequestStatus(String receiver, JavaMailSender javaMailSender) {
        SimpleMailMessage status = new SimpleMailMessage();
        status.setFrom(OutfitOasisConst.OUTFIT_OASIS_SENDER);
        status.setTo(receiver);
        status.setSubject("Shop producer status");
        status.setText("Your request has been added to waiting list...Wait for response");
        javaMailSender.send(status);
    }


}
