package com.example.mongo_db.Service.Clients;

import com.example.mongo_db.Entity.Client.Bucket;
import com.example.mongo_db.Entity.Client.Client;
import com.example.mongo_db.Entity.Client.Image;
import com.example.mongo_db.DAO.ClientShopItemDAO;
import com.example.mongo_db.Entity.Parse.Countries;
import com.example.mongo_db.Entity.Parse.Country;
import com.example.mongo_db.Repository.ClientsRepoes.BucketRepo;
import com.example.mongo_db.Repository.ClientsRepoes.ClientsRepo;
import com.example.mongo_db.Repository.ClientsRepoes.ImagesRepo;
import com.example.mongo_db.Service.EntityOperations;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClientsService implements EntityOperations {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private ClientsRepo clientsRepo;

    @Autowired
    private BucketRepo bucketRepo;

    @Autowired
    private ImagesRepo imagesRepo;


    private static final String sender = "onlineshop.project@yandex.com";


    public boolean doesClientExists(Client client) {
        if (client != null && clientsRepo.doesUserExists(client.getPhone_number(), client.getMail(), client.getClient_user_name()) == null) {
            return false;
        } else return true;
    }

    public void saveNewClient(Client client) {
        Bucket client_bucket = new Bucket();
        Image image = new Image();

        ArrayList<ClientShopItemDAO> items = new ArrayList<>();
        client_bucket.setClient_items(items);
        image.setImage("");


        client.setClient_image(image);
        client.setBucket(client_bucket);

        bucketRepo.save(client_bucket);
        imagesRepo.save(image);

        clientsRepo.save(client);
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

    public Optional<Client> findClientById(String id) {
        return clientsRepo.findById(id);
    }


    public Client findClientByUserName(String username) {
        return clientsRepo.doesUserNameExists(username);
    }

    public Client findClientByMail(String client_mail) {
        return clientsRepo.doesUserMailExists(client_mail);
    }


    public String sendPasswordRecoveryMessage(String client_mail) {
        String recovery_code = GenerateCode.createCode();
        SendMessage.sendRecoveryPassword(client_mail, sender, recovery_code, mailSender);
        return recovery_code;
    }


    public String sendVerificationMessage(String client_mail) {
        String verification_code = GenerateCode.createCode();
        SendVerificationMessage.SendVerificationCode(client_mail, sender, verification_code, mailSender);
        return verification_code;
    }


    public ArrayList<String> getCountries() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Countries countries = objectMapper.readValue(new File("src/main/java/com/example/mongo_db/Entity/Parse/countries.json"), Countries.class);
        ArrayList<String> countryArrayList = (ArrayList<String>) countries.getCountries().stream().map(Country::getName).collect(Collectors.toList());
        return countryArrayList;
    }

    public Client requestClientUpdate(Client request_for_update_client, boolean isAddressEmpty) {
        Optional<Client> clientById = findClientById(request_for_update_client.getId());
        if (clientById.isPresent()) {
            Client client = clientById.get();
            Client updated_client = new UpdateClient().updateClient(request_for_update_client, client, isAddressEmpty);
            if (updated_client != null) {
                return updated_client;
            } else {
                return null;
            }
        }
        return null;

    }

    @Override
    public void save_entity(Object obj) {

        Client client = (Client) obj;

        Bucket client_bucket = new Bucket();
        Image image = new Image();

        ArrayList<ClientShopItemDAO> items = new ArrayList<>();
        client_bucket.setClient_items(items);
        image.setImage("");


        client.setClient_image(image);
        client.setBucket(client_bucket);

        bucketRepo.save(client_bucket);
        imagesRepo.save(image);

        clientsRepo.save(client);
    }

    @Override
    public void update_entity(Object obj) {
        clientsRepo.save((Client) obj);

    }

    @Override
    public void remove_entity(Object obj) {
        clientsRepo.delete((Client) obj);
    }
}
