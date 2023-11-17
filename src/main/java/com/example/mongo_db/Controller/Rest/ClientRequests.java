package com.example.mongo_db.Controller.Rest;


import com.example.mongo_db.Service.Bucket.BucketService;
import com.example.mongo_db.Service.Clients.ClientsService;
import com.example.mongo_db.Service.Items.CatalogService;
import com.example.mongo_db.Service.Producer.ProducerService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
@RequestMapping("/shop/api/client")
public class ClientRequests {
    @Autowired
    private ClientsService clientsService;

    @Autowired
    private BucketService bucketService;

    @Autowired
    private ProducerService producerService;

    @Autowired
    private CatalogService catalogService;

    private static final Logger logger = Logger.global;


    @GetMapping("/status")
    public HttpStatus isClientAuthorized(HttpServletRequest request) {
        if (request.getSession().getAttribute("global_client") != null) {
            return HttpStatus.FOUND;
        } else return HttpStatus.NOT_FOUND;
    }

    @Transactional
    @PostMapping("/item/{id}")
    public ResponseEntity addItemToClientsCart(@PathVariable(value = "id") String id, HttpServletRequest request) {
        if (clientsService.validateClientAndItemBeforeOperation(request, id, catalogService)) {
            clientsService.saveItemToClientBucket(request, id, catalogService, producerService, bucketService);
            logger.info("The request coming from the client has been processed. New item added successfully to client's bucket");
            return ResponseEntity.ok().build();
        } else
            logger.info("The request coming from the client has been processed. Verification failed");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @Transactional
    @DeleteMapping("/item/{id}")
    public ResponseEntity removeOneItemFromClientsCart(@PathVariable(value = "id") String id, HttpServletRequest request) {
        if (clientsService.validateClientAndItemBeforeOperation(request, id, catalogService)) {
            clientsService.removeItemFromClientBucket(request, id, catalogService, producerService, bucketService);
            logger.info("The request coming from the client has been processed. Item removed successfully from client's bucket");
            return ResponseEntity.ok().build();
        } else
            logger.info("The request coming from the client has been processed. Verification failed");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

}
