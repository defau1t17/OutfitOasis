package com.example.mongo_db.Service.Clients;

import com.example.mongo_db.Entity.Role;
import org.springframework.stereotype.Component;

@Component
public class RoleRedirection {
    public String redirectClientByRole(Role role, String client_id) {
        switch (role) {
            case ROLE_CLIENT:
                return "/shop/client/account/" + client_id;
            case ROLE_PRODUCER:
                return "/shop/producer/account/" + client_id;
            case ROLE_ADMIN:
            case ROLE_REDACTOR:
                return "/shop/administration/panel";
            case ROLE_EMPLOYEE:
                return "/shop/employees/panel";
        }
        return "redirect:/shop/login";
    }

}
