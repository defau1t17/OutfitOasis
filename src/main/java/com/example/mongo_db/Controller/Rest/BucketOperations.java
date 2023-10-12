package com.example.mongo_db.Controller.Rest;


import com.example.mongo_db.Entity.Client.Client;
import com.example.mongo_db.Service.Bucket.BucketService;
import com.example.mongo_db.Service.Clients.ClientsService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shop/api")
public class BucketOperations {

    private long total_item_quantity;

    @Autowired
    private BucketService bucketService;

    @Autowired
    private ClientsService clientsService;


    @PostMapping("/client/{id}/bucket/addQuantity")
    public ResponseEntity addMoreQuantityToItem(HttpServletRequest request) {


        return ResponseEntity.ok().build();
    }
}
