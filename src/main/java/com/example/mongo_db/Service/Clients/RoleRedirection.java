package com.example.mongo_db.Service.Clients;

import com.example.mongo_db.Entity.Client.Client;
import com.example.mongo_db.Entity.Role;
import org.springframework.stereotype.Component;

@Component
public class RoleRedirection {
    public String redirectClientByRole(Client client) {
        switch (client.getRole()) {
            case ROLE_CLIENT:
                return "/shop/client/account/" + client.getId();
            case ROLE_PRODUCER:
                return "/shop/producer/account/" + client.getId();
            case ROLE_ADMIN:
            case ROLE_REDACTOR:
                return "/shop/administration/panel";
            case ROLE_EMPLOYEE:
                return "/shop/employees/panel";
        }
        return "redirect:/shop/login";
    }

}
