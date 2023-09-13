package com.example.mongo_db.Service.Clients;

public class LoginRedirection {

    public static boolean addModels(String username) {
        if (username != null && !username.isBlank() && !username.isEmpty()) {
            return true;
        } else return false;
    }

    public static String printIssue(String issue) {
        if (issue.equals("WRONG_PASSWORD")) {
            return "Access denied! Password is incorrect!";
        } else if (issue.equals("USER_NOT_FOUND")) {
            return "Client with such username  not found!";
        }
        return "";
    }
}
