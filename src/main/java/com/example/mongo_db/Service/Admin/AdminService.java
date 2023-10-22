package com.example.mongo_db.Service.Admin;

import com.example.mongo_db.Entity.Requests.GlobalRequests;
import com.example.mongo_db.Entity.Requests.Types.RequestTags;
import com.example.mongo_db.Repository.ClientsRepoes.ClientsRepo;
import com.example.mongo_db.Repository.RequestsRepost.RequestsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminService {

    @Autowired
    private RequestsRepo requestsRepo;

    @Autowired
    private ClientsRepo clientsRepo;

    private static final int REQUESTS_PAGE_SIZE = 20;


    public Page<GlobalRequests> getRequestsByPageNumber(int page){
        return requestsRepo.findAll(PageRequest.of(page,REQUESTS_PAGE_SIZE));
    }

//    public Page<GlobalRequests> getRequestsByTag(RequestTags tag){
//
//        return
//    }

    public Optional<GlobalRequests> getRequestByID(String id){
        return requestsRepo.findById(id);
    }

    public void deleteRequestByID(String id){
        requestsRepo.deleteById(id);
    }












}
