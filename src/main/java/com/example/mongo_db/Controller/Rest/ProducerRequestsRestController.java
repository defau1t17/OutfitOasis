package com.example.mongo_db.Controller.Rest;


import com.example.mongo_db.DTO.NewProducerItemDTO;
import com.example.mongo_db.Entity.Items.Item.ShopItem;
import com.example.mongo_db.RestExceptions.NewItemValidationException;
import com.example.mongo_db.Service.Producer.ProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/producer")
public class ProducerRequestsRestController {

    @Autowired
    private ProducerService producerService;

    @PostMapping("/")
    public ResponseEntity<?> createNewItemRequest(@RequestBody NewProducerItemDTO newProducerItemDTO) throws NewItemValidationException {
        String newRequestForNewItemID = producerService.createNewRequestForNewItem(newProducerItemDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("hello");
    }


}
