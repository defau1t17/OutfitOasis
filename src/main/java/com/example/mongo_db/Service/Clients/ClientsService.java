package com.example.mongo_db.Service.Clients;

import com.example.mongo_db.Entity.Client.Bucket;
import com.example.mongo_db.Entity.Client.Client;
import com.example.mongo_db.Entity.Client.Image;
import com.example.mongo_db.DAO.ClientShopItemDAO;
import com.example.mongo_db.Entity.Items.Item.ShopItem;
import com.example.mongo_db.Entity.Parse.Countries;
import com.example.mongo_db.Entity.Parse.Country;
import com.example.mongo_db.Entity.OutfitOasisMail;
import com.example.mongo_db.Entity.Producer.Producer;
import com.example.mongo_db.Filter.GlobalClientsFilter;
import com.example.mongo_db.Repository.ClientsRepoes.BucketRepo;
import com.example.mongo_db.Repository.ClientsRepoes.ClientsRepo;
import com.example.mongo_db.Repository.ClientsRepoes.ImagesRepo;
import com.example.mongo_db.Repository.RequestsRepost.RequestsRepo;
import com.example.mongo_db.Service.Bucket.BucketService;
import com.example.mongo_db.Service.EntityOperations;
import com.example.mongo_db.Service.GenerateSpecialCode;
import com.example.mongo_db.Service.Image.ImageService;
import com.example.mongo_db.Service.Items.CatalogService;
import com.example.mongo_db.Service.MessageSenderService;
import com.example.mongo_db.Service.Producer.ProducerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
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

    @Autowired
    private ImageService imageService;

    @Autowired
    private MessageSenderService messageSenderService;

    @Autowired
    private GenerateSpecialCode generateSpecialCode;

    @Autowired
    private MongoTemplate mongoTemplate;
    private static final int REQUESTS_PAGE_SIZE = 20;

    public boolean isClientExists(Client client) {
        if (client != null && clientsRepo.doesUserExists(client.getPhone_number(), client.getMail(), client.getClient_user_name()) == null) {
            return false;
        } else return true;
    }

    public String getExistedFields(Client client) {
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
        String recovery_code = generateSpecialCode.createCode();
        messageSenderService.sendRecoveryPasswordMessage(client_mail, recovery_code, mailSender);
        return recovery_code;
    }


    public String sendVerificationMessage(String client_mail) {
        String verification_code = generateSpecialCode.createCode();
        messageSenderService.sendClientVerificationMessage(client_mail, verification_code, mailSender);
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
            Client updated_client = clientById.get().updateClient(request_for_update_client, isAddressEmpty);
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

        client.setRegistrationDate(LocalDate.now());
        client.setLastVisit(LocalDateTime.now());

        Bucket client_bucket = new Bucket();
        Image image = imageService.buildDefaultImage();

        ArrayList<ClientShopItemDAO> items = new ArrayList<>();
        client_bucket.setClient_items(items);

        client.setClient_image(image);
        client.setBucket(client_bucket);

        bucketRepo.save(client_bucket);
        imagesRepo.save(image);

        clientsRepo.save(client);
    }

    public boolean validateClientAndItemBeforeOperation(HttpServletRequest request, String item_id, CatalogService catalogService) {
        return request.getSession().getAttribute("global_client") != null && catalogService.findItemById(item_id) != null;
    }

    public void saveItemToClientBucket(HttpServletRequest request, String item_id, CatalogService catalogService, ProducerService producerService, BucketService bucketService) {
        Client client = (Client) request.getSession().getAttribute("global_client");
        ShopItem item = catalogService.findItemById(item_id);
        ArrayList<ClientShopItemDAO> client_items = client.getBucket().getClient_items();
        ClientShopItemDAO clientShopItemDAO = new ClientShopItemDAO();
        Producer producer = producerService.findProducerByID(item.getProducer_id()).get();
        int itemPosition = bucketService.findItemPosition(client_items, item);
        if (itemPosition == -1) {
            clientShopItemDAO.setItem(item);
            clientShopItemDAO.setProducer(producer);
            clientShopItemDAO.setQuantity(1);
            client_items.add(clientShopItemDAO);

        } else {
            int currentQuantity = client_items.get(itemPosition).getQuantity();
            client_items.get(itemPosition).setQuantity(currentQuantity + 1);
        }
        client.getBucket().setClient_items(client_items);
        bucketService.update_entity(client.getBucket());
        update_entity(client);
        UpdateGlobalClient.updateGlobalClient("global_client", client, request.getSession());
    }


    public void removeItemFromClientBucket(HttpServletRequest request, String item_id, CatalogService catalogService, ProducerService producerService, BucketService bucketService) {
        Client client = (Client) request.getSession().getAttribute("global_client");
        ShopItem item = catalogService.findItemById(item_id);
        ArrayList<ClientShopItemDAO> client_items = client.getBucket().getClient_items();
        int itemPosition = bucketService.findItemPosition(client_items, item);
        ClientShopItemDAO clientShopItemDAO = client_items.get(itemPosition);

        if (clientShopItemDAO.getQuantity() > 1) {
            clientShopItemDAO.setQuantity(clientShopItemDAO.getQuantity() - 1);
        } else {
            client_items.remove(itemPosition);
        }
        client.getBucket().setClient_items(client_items);
        bucketService.update_entity(client.getBucket());
        update_entity(client);
        UpdateGlobalClient.updateGlobalClient("global_client", client, request.getSession());
    }

    public Page<Client> getClientsByParams(int page, List<String> roles, String name, String secondName, int age, String gender) {
        Query query = new GlobalClientsFilter().filterUsersByParams(roles, name, secondName, age, gender)
                .skip((long) page * REQUESTS_PAGE_SIZE)
                .limit(REQUESTS_PAGE_SIZE);
        return new PageImpl<>(mongoTemplate.find(query, Client.class), PageRequest.of(page, REQUESTS_PAGE_SIZE), mongoTemplate.count(query.skip(-1).limit(-1), Client.class));
    }

    @Override
    public void update_entity(Object obj) {
        clientsRepo.save((Client) obj);
    }

    @Override
    public void remove_entity(Object obj) {
        clientsRepo.delete((Client) obj);
    }

    @Transactional
    public void updateClientImage(MultipartFile file, Client client) throws IOException {
        Image image = client.getClient_image();
        image.setImage(Base64.getEncoder().encodeToString(file.getBytes()));
        imagesRepo.save(image);
        client.setClient_image(image);
        update_entity(client);
    }


}
