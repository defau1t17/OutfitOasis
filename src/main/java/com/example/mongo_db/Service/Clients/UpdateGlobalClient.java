package com.example.mongo_db.Service.Clients;

import com.example.mongo_db.Entity.Client.Client;
import jakarta.servlet.http.HttpSession;

public class UpdateGlobalClient {
    public static void updateGlobalClient(String global_client, Client updated_client, HttpSession httpSession) {
        httpSession.setAttribute(global_client, updated_client);
    }
}
