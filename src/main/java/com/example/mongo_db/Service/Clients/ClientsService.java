package com.example.mongo_db.Service.Clients;

import com.example.mongo_db.Entity.Client.Client;
import com.example.mongo_db.Repository.ClientsRepoes.ClientsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ClientsService {

    @Autowired
    private ClientsRepo clientsRepo;

    public ResponseEntity<Client> saveNewClient(Client client) {
        if (client != null && clientsRepo.doesUserExists(client.getPhone_number(), client.getMail(), client.getClient_user_name()) == null) {
            clientsRepo.save(client);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else if (clientsRepo.doesUserExists(client.getPhone_number(), client.getMail(), client.getClient_user_name()) != null) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public String existedFields(Client client) {
        String fields = "client with ";
        if (clientsRepo.doesUserNameExists(client.getClient_user_name()) != null) {
            fields += " username '" + client.getClient_user_name() + "' and";
        }
        if (clientsRepo.doesUserMailExists(client.getMail()) != null) {
            fields += " mail '" + client.getMail() + "' and";
        }
        if (clientsRepo.doesUserPhoneNumberExists(client.getPhone_number()) != null) {
            fields += " phone number '" + client.getPhone_number() + "'";
        }
        if (fields.endsWith("and")) {
            fields = fields.substring(0, fields.length() - 3);
        }
        fields += " already exists";
        return fields;
    }


    public Client findClientByUserName(String username) {
        return clientsRepo.doesUserNameExists(username);
    }


}
