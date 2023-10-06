package com.example.mongo_db.Controller.Rest;

import com.example.mongo_db.Entity.Client.Client;
import com.example.mongo_db.Entity.Requests.GlobalRequests;
import com.example.mongo_db.Entity.Requests.RequestData;
import com.example.mongo_db.Service.Clients.UpdateGlobalClient;
import com.example.mongo_db.Service.Requests.RequestsService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
@RequestMapping("/shop/send")
public class RequestOperations {

    @Autowired
    private RequestsService requestsService;

    private static final Logger logger = Logger.global;

    @RequestMapping(value = "/request/reports", method = RequestMethod.POST)
    public ResponseEntity getRequestIssue(@RequestBody GlobalRequests<String> bug_request, HttpServletRequest request) {
        bug_request.setRequest_sender((Client) request.getSession().getAttribute("global_client"));
        requestsService.save_entity(bug_request);
        logger.info("bug message has been added to request db successfully");

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @RequestMapping(value = "/request/producer")
    public ResponseEntity getRequestProducer(@RequestBody GlobalRequests<RequestData> producer_request, HttpServletRequest request) {
        if (!requestsService.isClientOnModeration((Client) request.getSession().getAttribute("global_client"))) {
            logger.info("client not found in moderation list. Ready for further moves");
            producer_request.setRequest_sender((Client) request.getSession().getAttribute("global_client"));
            requestsService.save_entity(producer_request);
            UpdateGlobalClient.updateGlobalClient("global_client", (Client) request.getSession().getAttribute("global_client"), request.getSession());
            logger.info("client added to moderation list successfully. Data updated");
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } else return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }


    //create mail message for client


}
