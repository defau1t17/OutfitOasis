package com.example.mongo_db.Controller.Shop.Items;

import com.example.mongo_db.Entity.Client.Bucket;
import com.example.mongo_db.Entity.Client.Client;
import com.example.mongo_db.Entity.Items.Item.ShopItem;
import com.example.mongo_db.Repository.ItemsRepoes.ItemRepo;
import com.example.mongo_db.Service.Bucket.BucketService;
import com.example.mongo_db.Service.Clients.ClientsService;
import com.example.mongo_db.Service.Clients.UpdateGlobalClient;
import jakarta.annotation.security.PermitAll;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Optional;

@RestController
@RequestMapping("/shop/catalog/add")
public class AddToCartController {

    private HashMap<Integer, ShopItem> item = new HashMap<>();
    private Bucket client_bucket;

    @Autowired
    private ItemRepo itemRepo;

    @Autowired
    private ClientsService clientsService;

    @Autowired
    private BucketService bucketService;

    @PostMapping("/item/{id}")
    public ResponseEntity test(@PathVariable(value = "id") String id, HttpServletRequest request) {
        Client client = (Client) request.getSession().getAttribute("global_client");
        Optional<ShopItem> shopItemById = itemRepo.findShopItemById(id);
        if (shopItemById.isPresent() && client != null) {
            item.put(1, shopItemById.get());
            client_bucket = client.getBucket();

            client_bucket.setItems(item);

            client.setBucket(client_bucket);

            bucketService.updateBucket(client_bucket);
            clientsService.updateClient(client);

            item.clear();
            client_bucket = null;

            UpdateGlobalClient.updateGlobalClient("global_client", client, request.getSession());
            return ResponseEntity.ok().build();
        } else return ResponseEntity.status(226).build();
    }

}
