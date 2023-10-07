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
        Optional<GlobalRequests> client_in_request = requestsRepo.findByRequest_sender(client);
        if (client_in_request.isPresent()) {
            GlobalRequests globalRequests = client_in_request.get();

            if (globalRequests.getRequest_sender().getId().equals(client.getId()) &&
                    globalRequests.getTag().equals(RequestTags.PRODUCER_NEW)) {
                return true;
            } else return false;
        }
        return false;
    }

    public boolean isClientAlreadyProducer(Client client) {
        Optional<GlobalRequests> byRequest_sender = requestsRepo.findByRequest_sender(client);
        if (byRequest_sender.isPresent()) {
            return requestsRepo.findByRequest_sender(client).get().getRequest_sender().getRole().equals(Role.ROLE_PRODUCER);
        }
        return false;
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
