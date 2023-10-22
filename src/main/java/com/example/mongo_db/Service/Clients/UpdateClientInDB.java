package com.example.mongo_db.Service.Clients;

import com.example.mongo_db.Entity.Client.Client;

public class UpdateClientInDB {

    public Client updateClient(Client updatedClient, Client current_client, boolean isAddressEmpty) {

        if (updatedClient != null && current_client != null) {
            if (updatedClient.getName() != null && updatedClient.getName().trim() != "") {
                current_client.setName(updatedClient.getName());
            }
            if (updatedClient.getSecond_name() != null && updatedClient.getSecond_name().trim() != "") {
                current_client.setSecond_name(updatedClient.getSecond_name());
            }
            if (updatedClient.getAge() != 0 && updatedClient.getAge() >= 12) {
                current_client.setAge(updatedClient.getAge());
            }
            if (updatedClient.getPhone_number() != null && updatedClient.getPhone_number().trim() != "") {
                current_client.setPhone_number(updatedClient.getPhone_number());
            }
            if (updatedClient.getAddress() != null && !isAddressEmpty) {
                current_client.setAddress(updatedClient.getAddress());
            } else if (updatedClient.getAddress() == null && isAddressEmpty) {
                current_client.setAddress(null);
            }
            return current_client;
        } else {
            return null;
        }
    }
}
