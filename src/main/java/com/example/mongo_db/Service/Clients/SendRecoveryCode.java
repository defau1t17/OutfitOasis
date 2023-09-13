package com.example.mongo_db.Service.Clients;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class SendRecoveryCode {

    @Autowired
    private JavaMailSender mailSender;




}
