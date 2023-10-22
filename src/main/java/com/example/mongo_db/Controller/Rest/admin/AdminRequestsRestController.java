package com.example.mongo_db.Controller.Rest.admin;

import com.example.mongo_db.DTO.SendRequestOperationDTO;
import com.example.mongo_db.Entity.Client.Client;
import com.example.mongo_db.Service.Admin.AdminService;
import com.example.mongo_db.Service.Admin.RequestOperations;
import com.example.mongo_db.Service.BugsAndQos.BugsAndQosService;
import com.example.mongo_db.Service.Clients.ClientsService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shop/administration/admin/request")
public class AdminRequestsRestController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private ClientsService clientsService;

    @Autowired
    private BugsAndQosService bugsAndQosService;


    @GetMapping("/global/id")
    public ResponseEntity<String> getCurrentAdminID(HttpServletRequest request) {
        Client global_client = (Client) request.getSession().getAttribute("global_client");
        if (global_client != null) {
            String global_clientId = global_client.getId();
            return ResponseEntity.ok(global_clientId);
        } else return ResponseEntity.notFound().build();
    }


    @PostMapping("/{id}/moderation/datatype")
    public ResponseEntity adminRequestsProcessor(@PathVariable(value = "id") String id, @RequestBody SendRequestOperationDTO request) {
        if (new RequestOperations().processOperation(request, adminService, bugsAndQosService, clientsService, mailSender)) {
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        } else return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).build();
    }


}
