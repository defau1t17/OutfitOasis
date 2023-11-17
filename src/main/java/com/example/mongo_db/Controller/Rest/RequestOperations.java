package com.example.mongo_db.Controller.Rest;

import com.example.mongo_db.Entity.Requests.GlobalRequests;
import com.example.mongo_db.Entity.Requests.RequestData;
import com.example.mongo_db.Service.Requests.RequestsService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
@RequestMapping("/shop/api/help")
public class RequestOperations {

    @Autowired
    private RequestsService requestsService;

    private static final Logger logger = Logger.getGlobal();

    @PostMapping(value = "/report")
    public ResponseEntity getRequestIssue(@RequestBody GlobalRequests<String> bug_request, HttpServletRequest request) {
        requestsService.saveNewReport(bug_request, request);
        logger.info("The request coming from the client has been processed. New repost saved successfully");
        return ResponseEntity.status(200).build();
    }

    @PostMapping(value = "/producer")
    public ResponseEntity getRequestProducer(@RequestBody GlobalRequests<RequestData> producer_request, HttpServletRequest request) {
        if (!requestsService.validateClientBeforeAdd(request)) {
            logger.info("The request coming from the client has been processed. The client is not on the moderation list");
            requestsService.saveNewProducerRequest(producer_request, request);
            return ResponseEntity.status(200).build();
        } else
            logger.info("The request coming from the client has been processed. The client failed validation");
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }


}
