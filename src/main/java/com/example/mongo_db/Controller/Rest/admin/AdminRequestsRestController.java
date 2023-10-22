package com.example.mongo_db.Controller.Rest.admin;

import com.example.mongo_db.DTO.admin.SendRequestOperation;
import com.example.mongo_db.Entity.Client.Client;
import com.example.mongo_db.Repository.RequestsRepost.RequestsRepo;
import com.example.mongo_db.Service.Admin.AdminService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shop/administration/admin/request")
public class AdminRequestsRestController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/global/id")
    public ResponseEntity<String> getCurrentAdminID(HttpServletRequest request){
        Client global_client = (Client) request.getSession().getAttribute("global_client");
        if(global_client != null){
            String global_clientId = global_client.getId();
            return ResponseEntity.ok(global_clientId);
        }
        else return ResponseEntity.notFound().build();
    }


    @PostMapping("/{id}/moderation/datatype")
    public ResponseEntity adminRequestsProcessor(@PathVariable(value = "id")String id, @RequestBody SendRequestOperation request){
        System.out.println("I'm here :)");
        System.out.println(request.getRequest_id());
        System.out.println(request.getOperation());
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }


    private boolean processOperation(SendRequestOperation data){
        switch (data.getOperation()){
            case "APPLY":

                break;
            case "DENY":

                break;

            case "REMOVE":

                break;
        }
        return false;
    }








}
