package com.example.mongo_db.Service.Admin;

import com.example.mongo_db.Entity.Client.Client;
import com.example.mongo_db.Entity.Requests.GlobalRequests;
import com.example.mongo_db.Entity.Role;
import com.example.mongo_db.Filter.RequestsFilter;
import com.example.mongo_db.Filter.GlobalClientsFilter;
import com.example.mongo_db.Repository.RequestsRepost.RequestsRepo;
import com.example.mongo_db.Service.Clients.ClientsService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private RequestsRepo requestsRepo;

    private static final int REQUESTS_PAGE_SIZE = 20;

    @Autowired
    private ClientsService clientsService;

    public Page<GlobalRequests> getRequestsByParams(int page, List<String> tags) {
        Query query = new RequestsFilter().filterRequestsByTags(tags)
                .skip((long) page * REQUESTS_PAGE_SIZE)
                .limit(REQUESTS_PAGE_SIZE);
        return new PageImpl<>(mongoTemplate.find(query, GlobalRequests.class), PageRequest.of(page, REQUESTS_PAGE_SIZE), mongoTemplate.count(query.skip(-1).limit(-1), GlobalRequests.class));
    }

    public Page<Client> getClientsByParams(int page, List<String> roles, String name, String secondName, int age, String gender) {
        return clientsService.getClientsByParams(page, roles, name, secondName, age, gender);
    }

    public Optional<GlobalRequests> getRequestByID(String id) {
        return requestsRepo.findById(id);
    }

    public void deleteRequestByID(String id) {
        requestsRepo.deleteById(id);
    }

    public void createSession(Client client, HttpSession session) {
        session.setAttribute("global_client", client);
    }

    public boolean operationValidation(Client client) {
        return client.getRole().equals(Role.ROLE_ADMIN);
    }


}
