package com.example.mongo_db.Controller.Shop.Items;

import com.example.mongo_db.Entity.Client.Bucket;
import com.example.mongo_db.Entity.Client.Client;
import com.example.mongo_db.DAO.ClientShopItemDAO;
import com.example.mongo_db.Entity.Items.Item.ShopItem;
import com.example.mongo_db.Entity.Producer.Producer;
import com.example.mongo_db.Repository.ItemsRepoes.ItemRepo;
import com.example.mongo_db.Repository.ProducerRepoes.ProducerRepo;
import com.example.mongo_db.Service.Bucket.BucketService;
import com.example.mongo_db.Service.Clients.ClientsService;
import com.example.mongo_db.Service.Clients.UpdateGlobalClient;
import com.example.mongo_db.Service.Producer.ProducerService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;
import java.util.logging.Logger;

@RestController
@RequestMapping("/shop/catalog/")
public class CartOperations {

    private ArrayList<ClientShopItemDAO> list_of_clients_items;
    private Bucket client_bucket;


    @Autowired
    private ItemRepo itemRepo;

    @Autowired
    private ClientsService clientsService;

    @Autowired
    private BucketService bucketService;

    @Autowired
    private ProducerService producerService;

    private static final Logger logger = Logger.global;

    @Transactional
    @PostMapping("/add/item/{id}")
    public ResponseEntity addItemToClientsCart(@PathVariable(value = "id") String id, HttpServletRequest request) {
        Client client = (Client) request.getSession().getAttribute("global_client");
        Optional<ShopItem> shopItemById = itemRepo.findShopItemById(id);
        ClientShopItemDAO shopItemDAO;

        logger.info("client has requested to add a new item to his bucket");

        if (shopItemById.isPresent() && client != null) {

            shopItemDAO = new ClientShopItemDAO();
            client_bucket = client.getBucket();
            list_of_clients_items = client_bucket.getClient_items();

            shopItemDAO.setItem(shopItemById.get());

            Optional<Producer> producerById = producerService.findProducerByID(shopItemById.get().getProducer_id());

            Producer producer = null;

            logger.info("all data captured. Ready to check it");

            if (producerById.isPresent()) {
                producer = producerById.get();
            }
            System.out.println(id);
            if (checkForItem(list_of_clients_items, shopItemById.get()) == -1) {
                shopItemDAO.setQuantity(1);
                list_of_clients_items.add(shopItemDAO);
                shopItemDAO.setProducer(producer);
                logger.info("item ist in bucket. Added new item");

            } else {
                int updated_quantity = list_of_clients_items.get((int) checkForItem(list_of_clients_items, shopItemById.get())).getQuantity() + 1;
                list_of_clients_items.get((int) checkForItem(list_of_clients_items, shopItemById.get())).setQuantity(updated_quantity);
                logger.info("item found successfully. Quantity has incremented");

            }


            client_bucket.setClient_items(list_of_clients_items);

            client.setBucket(client_bucket);


            bucketService.update_entity(client_bucket);
            clientsService.update_entity(client);

            UpdateGlobalClient.updateGlobalClient("global_client", client, request.getSession());
            logger.info("operation with item has made successfully ");

            return ResponseEntity.ok().build();
        } else
            logger.info("client isn't authorized or item with such id not found. Status send back");
        return ResponseEntity.status(226).build();
    }

    @Transactional
    @PostMapping("/remove/item/{id}")
    public ResponseEntity removeOneItemFromClientsCart(@PathVariable(value = "id") String id, HttpServletRequest request) {
        Client client = (Client) request.getSession().getAttribute("global_client");
        Optional<ShopItem> shopItemById = itemRepo.findShopItemById(id);
        ClientShopItemDAO shopItemDAO;
        logger.info("client has requested to remove one item from  his bucket");
        if (shopItemById.isPresent() && client != null) {
            client_bucket = client.getBucket();
            list_of_clients_items = client_bucket.getClient_items();
            shopItemDAO = list_of_clients_items.get((int) checkForItem(list_of_clients_items, shopItemById.get()));

            if (shopItemDAO.getQuantity() > 1) {
                shopItemDAO.setQuantity(shopItemDAO.getQuantity() - 1);
            } else if (shopItemDAO.getQuantity() == 1) {
                list_of_clients_items.remove((int) checkForItem(list_of_clients_items, shopItemById.get()));
            }

            client_bucket.setClient_items(list_of_clients_items);

            client.setBucket(client_bucket);
            bucketService.update_entity(client_bucket);
            clientsService.update_entity(client);

            UpdateGlobalClient.updateGlobalClient("global_client", client, request.getSession());

            logger.info("operation with item has made successfully");

            return ResponseEntity.ok().build();
        } else
            logger.info("something went wrong with operation!");
        return ResponseEntity.status(226).build();

    }


    private static long checkForItem(ArrayList<ClientShopItemDAO> items, ShopItem item) {
        logger.info("searching for item in items list");
        for (ClientShopItemDAO dao : items) {
            if (dao.getItem().getId().equals(item.getId())) {
                return items.indexOf(dao);
            } else {
            }
        }
        return -1;
    }


}
