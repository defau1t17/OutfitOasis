package com.example.mongo_db.Service.Requests;

import com.example.mongo_db.Entity.Requests.GlobalRequests;
import com.example.mongo_db.Repository.RequestsRepost.RequestRepo;
import com.example.mongo_db.Service.EntityOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class RequestsService implements EntityOperations {

    @Autowired
    private RequestRepo requestRepo;

    @Override
    public void save_entity(Object obj) {
        requestRepo.save((GlobalRequests) obj);

    }

    @Override
    public void update_entity(Object obj) {
        requestRepo.save((GlobalRequests) obj);

    }

    @Override
    public void remove_entity(Object obj) {
        requestRepo.delete((GlobalRequests) obj);

    }
}
