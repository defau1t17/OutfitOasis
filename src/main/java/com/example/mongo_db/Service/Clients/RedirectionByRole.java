package com.example.mongo_db.Service.Clients;

import com.example.mongo_db.Entity.Client.Client;
import com.example.mongo_db.Entity.Producer.Producer;
import com.example.mongo_db.Entity.Role;

import static com.example.mongo_db.Entity.Role.ROLE_CLIENT;
import static com.example.mongo_db.Entity.Role.ROLE_PRODUCER;

public class RedirectionByRole {


    public static String redirectByRole(String role, Object object) {
        String redirection_url = "/shop/";

        switch (Role.valueOf(role)) {
            case ROLE_CLIENT:
                Client client = (Client) object;
                redirection_url += "client/account/" + client.getId();
                return redirection_url;
            case ROLE_PRODUCER:
                Producer producer = (Producer) object;
                redirection_url += "producer/account/" + producer.getId();
                return redirection_url;

            case ROLE_ADMIN:
            case ROLE_EMPLOYEE:
            case ROLE_REDACTOR:

        }

        return "redirect:/shop/login";
    }

}
