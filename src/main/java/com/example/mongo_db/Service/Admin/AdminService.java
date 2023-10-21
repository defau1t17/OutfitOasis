package com.example.mongo_db.Service.Admin;

import com.example.mongo_db.Repository.ClientsRepoes.ClientsRepo;
import com.example.mongo_db.Repository.RequestsRepost.RequestsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    @Autowired
    private RequestsRepo requestsRepo;



    @Autowired
    private ClientsRepo clientsRepo;



}
