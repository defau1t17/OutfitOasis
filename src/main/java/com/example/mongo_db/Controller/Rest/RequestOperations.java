package com.example.mongo_db.Controller.Rest;

import com.example.mongo_db.Entity.Client.Client;
import com.example.mongo_db.Entity.Requests.GlobalRequests;
import com.example.mongo_db.Entity.Requests.RequestData;
import com.example.mongo_db.Entity.Requests.Types.RequestTags;
import com.example.mongo_db.Repository.RequestsRepost.RequestRepo;
import com.example.mongo_db.Service.Requests.RequestsService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shop/send")
public class RequestOperations {

    @Autowired
    private RequestsService requestsService;

    @RequestMapping(value = "/request/reports", method = RequestMethod.POST)
    public ResponseEntity getRequestIssue(@RequestBody GlobalRequests<String> bug_request, HttpServletRequest request) {
        bug_request.setRequest_sender((Client) request.getSession().getAttribute("global_client"));
        requestsService.save_entity(bug_request);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @RequestMapping(value = "/request/producer")
    public ResponseEntity getRequestProducer(@RequestBody GlobalRequests<RequestData> producer_request, HttpServletRequest request) {
        System.out.println(producer_request);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


}
