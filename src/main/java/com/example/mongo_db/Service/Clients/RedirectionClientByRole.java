package com.example.mongo_db.Service.Clients;

import com.example.mongo_db.Entity.Client.Client;
import com.example.mongo_db.Entity.Producer.Producer;
import com.example.mongo_db.Entity.Role;

import static com.example.mongo_db.Entity.Role.ROLE_CLIENT;
import static com.example.mongo_db.Entity.Role.ROLE_PRODUCER;

public class RedirectionClientByRole {


    public static String redirectClientByRole(Role role, String client_id) {
        String redirection_url = "/shop/";

        switch (role) {
            case ROLE_CLIENT:
                redirection_url += "client/account/" + client_id;
                return redirection_url;
            case ROLE_PRODUCER:
                redirection_url += "producer/account/" + client_id;
                return redirection_url;
            case ROLE_ADMIN:
                redirection_url += "administration/admin/panel/" + client_id;
                return redirection_url;

            case ROLE_EMPLOYEE:

            case ROLE_REDACTOR:

        }

        return "redirect:/shop/login";
    }

}
