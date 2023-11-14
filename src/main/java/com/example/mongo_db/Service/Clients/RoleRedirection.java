package com.example.mongo_db.Service.Clients;

import com.example.mongo_db.Entity.Role;
import org.springframework.stereotype.Component;

@Component
public class RoleRedirection {
    public String redirectClientByRole(Role role, String client_id) {
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
