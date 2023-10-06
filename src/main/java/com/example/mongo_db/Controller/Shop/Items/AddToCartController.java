package com.example.mongo_db.Controller.Shop.Items;

import com.example.mongo_db.Entity.Client.Bucket;
import com.example.mongo_db.Entity.Client.Client;
import com.example.mongo_db.DAO.ClientShopItemDAO;
import com.example.mongo_db.Entity.Items.Item.ShopItem;
import com.example.mongo_db.Repository.ItemsRepoes.ItemRepo;
import com.example.mongo_db.Service.Bucket.BucketService;
import com.example.mongo_db.Service.Clients.ClientsService;
import com.example.mongo_db.Service.Clients.UpdateGlobalClient;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("/shop/catalog/add")
public class AddToCartController {

    private ArrayList<ClientShopItemDAO> list_of_clients_items;
    private Bucket client_bucket;


    @Autowired
    private ItemRepo itemRepo;

    @Autowired
    private ClientsService clientsService;

    @Autowired
    private BucketService bucketService;

    @Transactional
    @PostMapping("/item/{id}")
    public ResponseEntity test(@PathVariable(value = "id") String id, HttpServletRequest request) {
        Client client = (Client) request.getSession().getAttribute("global_client");
        Optional<ShopItem> shopItemById = itemRepo.findShopItemById(id);
        ClientShopItemDAO shopItemDAO;
        if (shopItemById.isPresent() && client != null) {
            shopItemDAO = new ClientShopItemDAO();
            client_bucket = client.getBucket();
            list_of_clients_items = client_bucket.getClient_items();

            shopItemDAO.setItem(shopItemById.get());


            System.out.println(id);
            if (checkForItem(list_of_clients_items, shopItemById.get()) == -1) {
                System.out.println("not found");
                shopItemDAO.setQuantity(1);
                list_of_clients_items.add(shopItemDAO);
            } else {
                int updated_quantity = list_of_clients_items.get((int) checkForItem(list_of_clients_items, shopItemById.get())).getQuantity() + 1;
                list_of_clients_items.get((int) checkForItem(list_of_clients_items, shopItemById.get())).setQuantity(updated_quantity);
            }


            client_bucket.setClient_items(list_of_clients_items);

            client.setBucket(client_bucket);


            bucketService.updateBucket(client_bucket);
            clientsService.updateClient(client);

            UpdateGlobalClient.updateGlobalClient("global_client", client, request.getSession());

            return ResponseEntity.ok().build();
        } else return ResponseEntity.status(226).build();
    }

    private static long checkForItem(ArrayList<ClientShopItemDAO> items, ShopItem item) {
        for (ClientShopItemDAO dao : items) {
            if (dao.getItem().getId().equals(item.getId())) {
                System.out.println("true");
                return items.indexOf(dao);
            } else {
                continue;
            }
        }
        return -1;
    }

}
