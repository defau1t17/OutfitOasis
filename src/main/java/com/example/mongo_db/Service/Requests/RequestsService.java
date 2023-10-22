package com.example.mongo_db.Service.Requests;

import com.example.mongo_db.Entity.Client.Client;
import com.example.mongo_db.Entity.Requests.GlobalRequests;
import com.example.mongo_db.Entity.Requests.Types.RequestTags;
import com.example.mongo_db.Entity.Role;
import com.example.mongo_db.Repository.RequestsRepost.RequestsRepo;
import com.example.mongo_db.Service.EntityOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class RequestsService implements EntityOperations {


    @Autowired
    private RequestsRepo requestsRepo;

    @Autowired
    private JavaMailSender javaMailSender;

    private static final String SENDER = "onlineshop.project@yandex.com";


    public boolean isClientOnModeration(Client client) {
        Optional<GlobalRequests> client_in_request = requestsRepo.isClientInRequestList(client.getId(), RequestTags.PRODUCER_NEW);
        if (client_in_request.isPresent()) {
            return true;
        } else
            return false;
    }



    public boolean isClientAlreadyProducer(Client client) {
        return client.getRole().equals(Role.ROLE_PRODUCER);
    }


    public void sendMessage(String producer_mail) {
        SendStatusMessage.SendModerationMessage(producer_mail, SENDER, javaMailSender);
    }


    @Override
    public void save_entity(Object obj) {
        requestsRepo.save((GlobalRequests) obj);

    }

    @Override
    public void update_entity(Object obj) {
        requestsRepo.save((GlobalRequests) obj);

    }

    @Override
    public void remove_entity(Object obj) {
        requestsRepo.delete((GlobalRequests) obj);

    }
}
