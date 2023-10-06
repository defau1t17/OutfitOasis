package com.example.mongo_db.Service.Requests;

import com.example.mongo_db.Entity.Client.Client;
import com.example.mongo_db.Entity.Requests.GlobalRequests;
import com.example.mongo_db.Repository.RequestsRepost.RequestsRepo;
import com.example.mongo_db.Service.EntityOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class RequestsService implements EntityOperations {


    @Autowired
    private RequestsRepo requestsRepo;

    public boolean isClientOnModeration(Client client) {
        if (!requestsRepo.findByRequest_sender(client).isPresent()) {
            return false;
        } else return true;
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
