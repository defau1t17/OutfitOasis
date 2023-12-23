package com.example.mongo_db.Service.Clients;

import com.example.mongo_db.Entity.Client.Client;
import com.example.mongo_db.Security.ClientDetailsService;
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


    public String authenticateClient(String username, String password, ClientDetailsService clientDetailsService, HttpServletRequest request) {
        Client client = clientsService.findClientByUserName(username);
        if (client != null) {
            if (client.getClient_password().equals(password)) {
                clientDetailsService.authenticateClient(client, request);
                client.setLastVisit(LocalDateTime.now());
                clientsService.updateGlobalClient(client, request.getSession());
                return redirectAuthenticatedClientByRole(client);
            }
        }
        return "redirect:/shop/client/login?username=" + username + "&issue=BAD_CREDENTIALS";

    }

    public String redirectAuthenticatedClientByRole(Client client) {
        switch (client.getRole()) {
            case ROLE_CLIENT:
                return "/shop/client/account/" + client.getId();
            case ROLE_PRODUCER:
                return "/shop/producer/" + producerService.findProducerByClientsID(client.getId()) + "/creator";
            case ROLE_ADMIN:
            case ROLE_REDACTOR:
                return "/shop/administration/panel";
            case ROLE_EMPLOYEE:
        }
        return "redirect:/shop/client/login?issue=EMPTY_ROLE";
    }


}
