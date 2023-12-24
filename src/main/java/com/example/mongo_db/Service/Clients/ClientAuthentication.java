package com.example.mongo_db.Service.Clients;

import com.example.mongo_db.Entity.Client.Client;
import com.example.mongo_db.Entity.Producer.Producer;
import com.example.mongo_db.Security.ClientDetailsService;
import com.example.mongo_db.Service.Admin.AdminService;
import com.example.mongo_db.Service.Producer.ProducerService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ClientAuthentication {

    @Autowired
    private ClientsService clientsService;
    @Autowired
    private ProducerService producerService;

    @Autowired
    private AdminService adminService;


    public String authenticateClient(String username, String password, ClientDetailsService clientDetailsService, HttpServletRequest request) {
        Client client = clientsService.findClientByUserName(username);
        if (client != null) {
            if (client.getClient_password().equals(password)) {
                clientDetailsService.authenticateClient(client, request);
                client.setLastVisit(LocalDateTime.now());
                return redirectAuthenticatedClientByRole(client, request);
            }
        }
        return "redirect:/shop/client/login?username=" + username + "&issue=BAD_CREDENTIALS";

    }

    public String redirectAuthenticatedClientByRole(Client client, HttpServletRequest request) {
        switch (client.getRole()) {
            case ROLE_CLIENT:
                clientsService.updateGlobalClient(client, request.getSession());
                return "redirect:/shop/client/account/" + client.getId();
            case ROLE_PRODUCER:
                Producer producerByClientsID = producerService.findProducerByClientsID(client.getId());
                clientsService.update_entity(client);
                producerService.createSession(producerByClientsID, request.getSession());
                return "redirect:/shop/producer/" + producerByClientsID.getId() + "/creator";
            case ROLE_ADMIN:
            case ROLE_REDACTOR:
                adminService.createSession(client, request.getSession());
                clientsService.update_entity(client);
                return "redirect:/shop/administration/panel";
            case ROLE_EMPLOYEE:
        }
        return "redirect:/shop/client/login?issue=EMPTY_ROLE";
    }


}
