package com.example.mongo_db.Service;

import org.springframework.stereotype.Component;

@Component
public class LoginRedirection {
    public boolean addModels(String username) {
        if (username != null && !username.isBlank() && !username.isEmpty()) {
            return true;
        } else return false;
    }

    public String printIssue(String issue) {
        if (issue.equals("WRONG_PASSWORD")) {
            return "Access denied! Password is incorrect!";
        } else if (issue.equals("USER_NOT_FOUND")) {
            return "Client with such username  not found!";
        }
        return "";
    }
}
